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
package se.kecon.jddns4cf.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import se.kecon.jddns4cf.ParseSupport;
import se.kecon.jddns4cf.entity.DnsRecord;
import se.kecon.jddns4cf.entity.ListDnsRecords;
import se.kecon.jddns4cf.entity.ResultInfo;

/**
 * Parse response from list DNS records
 * 
 * @author Kenny Colliander Nordin
 */
public class ListDnsRecordsTest {

	@Test
	public void testOk() throws JsonParseException, JsonMappingException, IOException {
		ListDnsRecords records = ParseSupport.parse(ListDnsRecords.class, "listDnsRecordsOk.json");

		assertTrue(records.isSuccess());
		assertEquals(0, records.getErrors().length);
		assertEquals(1, records.getResult().length);

		DnsRecord dnsRecord = records.getResult()[0];

		assertEquals("372e67954025e0ba6aaa6d586b9e0b59", dnsRecord.getId());
		assertEquals("example.com", dnsRecord.getName());
		assertEquals("1.2.3.4", dnsRecord.getContent());
		assertTrue(dnsRecord.isProxiable());
		assertFalse(dnsRecord.isProxied());
		assertEquals(120, dnsRecord.getTtl());
		assertEquals("example.com", dnsRecord.getZoneName());

		ResultInfo resultInfo = records.getResultInfo();
		assertEquals(1, resultInfo.getPage());
		assertEquals(20, resultInfo.getPerPage());
		assertEquals(1, resultInfo.getCount());
		assertEquals(2000, resultInfo.getTotalCount());
	}
}
