/**
 * jddns4cf Copyright (c) 2017 Kenny Colliander Nordin
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.kecon.jddns4cf;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.fasterxml.jackson.core.JsonParseException;

import se.kecon.jddns4cf.CfApi;
import se.kecon.jddns4cf.entity.BaseResponse;
import se.kecon.jddns4cf.entity.DnsRecord;
import se.kecon.jddns4cf.entity.ListDnsRecords;
import se.kecon.jddns4cf.entity.UpdateDnsRecord;

/**
 * Test for <code>CfApi</code>
 * 
 * @author Kenny Colliander Nordin
 */
@RunWith(MockitoJUnitRunner.class)
public class CfApiTest {

	private CfApi cfApi;

	private static final String ZONE_ID = "abc123";

	private static final String TYPE = "type";

	private static final String NAME = "name";

	@Mock
	private WebTarget target;

	@Mock
	private Response response;

	@Before
	public void setup() {
		cfApi = new CfApi(target);
	}

	@Test
	public void testListDnsRecordsOk() {

		WebTarget zonesTarget = addPath(target, "zones");
		WebTarget zoneIdTarget = addPath(zonesTarget, ZONE_ID);
		WebTarget dnzRecordsTarget = addPath(zoneIdTarget, "dns_records");

		WebTarget typeTarget = addQuery(dnzRecordsTarget, "type", TYPE);
		WebTarget nameTarget = addQuery(typeTarget, "name", NAME);

		Invocation.Builder builder = mock(Invocation.Builder.class);
		when(nameTarget.request()).thenReturn(builder);
		when(builder.get()).thenReturn(response);

		ListDnsRecords listDnsRecords = mock(ListDnsRecords.class);
		DnsRecord[] dnsRecords = new DnsRecord[] { mock(DnsRecord.class) };

		when(response.getStatus()).thenReturn(200);
		when(response.readEntity(ListDnsRecords.class)).thenReturn(listDnsRecords);
		when(listDnsRecords.getResult()).thenReturn(dnsRecords);

		assertArrayEquals(dnsRecords, cfApi.listDnsRecords(ZONE_ID, TYPE, NAME));
	}

	@Test
	public void testUpdateDnsRecord() throws JsonParseException, IOException {
		ListDnsRecords listDnsRecords = ParseSupport.parse(ListDnsRecords.class, "listDnsRecordsOk.json");
		BaseResponse baseResponse = ParseSupport.parse(BaseResponse.class, "updateDnsRecord.json");

		WebTarget zonesTarget = addPath(target, "zones");
		WebTarget zoneIdTarget = addPath(zonesTarget, "023e105f4ecef8ad9ca31a8372d0c353");
		WebTarget dnzRecordsTarget = addPath(zoneIdTarget, "dns_records");
		WebTarget idTarget = addPath(dnzRecordsTarget, "372e67954025e0ba6aaa6d586b9e0b59");

		Invocation.Builder builder = mock(Invocation.Builder.class);
		when(idTarget.request()).thenReturn(builder);

		UpdateDnsRecord updateDnsRecord = listDnsRecords.getResult()[0].toUpdateDnsRecord();
		updateDnsRecord.setContent("4.3.2.1");

		when(builder.put(Entity.entity(updateDnsRecord, MediaType.APPLICATION_JSON))).thenReturn(response);

		when(response.getStatus()).thenReturn(200);
		when(response.readEntity(BaseResponse.class)).thenReturn(baseResponse);

		assertTrue(cfApi.updateDnsRecord(listDnsRecords.getResult()[0], "4.3.2.1"));
	}

	private WebTarget addPath(WebTarget target, String expected) {
		WebTarget path = mock(WebTarget.class);
		when(target.path(expected)).thenReturn(path);

		return path;
	}

	private WebTarget addQuery(WebTarget target, String key, String value) {
		WebTarget path = mock(WebTarget.class);
		when(target.queryParam(key, value)).thenReturn(path);

		return path;
	}
}
