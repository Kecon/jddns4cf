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

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.internal.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.kecon.jddns4cf.entity.BaseResponse;
import se.kecon.jddns4cf.entity.DnsRecord;
import se.kecon.jddns4cf.entity.ListDnsRecords;
import se.kecon.jddns4cf.entity.UpdateDnsRecord;

/**
 * Basic API implementation.
 * 
 * @author Kenny Colliander Nordin
 */
public class CfApi {

	private static final Logger LOG = LoggerFactory.getLogger(CfApi.class);

	private final WebTarget target;

	protected CfApi(final WebTarget target) {
		this.target = target;
	}

	public CfApi(final String authEmail, final String authKey) {
		super();

		final Client client = ClientBuilder.newClient();
		final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
				false);

		final JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
		provider.setMapper(mapper);

		this.target = client.target("https://api.cloudflare.com/client/v4/")
				.register(new CfAuthenticator(authEmail, authKey)).register(provider);
	}

	protected WebTarget getTarget() {
		return this.target;
	}

	/**
	 * List DNS records
	 * 
	 * @param zone
	 *            the zoneId
	 * @param type
	 *            the type, either A or AAAA, may be null
	 * @param name
	 *            the name of the record, typically example.com, may be null
	 * @return all records found for the query
	 */
	public DnsRecord[] listDnsRecords(final String zone, final String type, final String name) {
		WebTarget target = this.getTarget().path("zones").path(zone).path("dns_records");

		if (type != null) {
			target = target.queryParam("type", type);
		}

		if (name != null) {
			target = target.queryParam("name", name);
		}

		Response response = target.request().get();

		if (response.getStatus() == 200) {
			ListDnsRecords listDnsRecords = response.readEntity(ListDnsRecords.class);
			return listDnsRecords.getResult();
		}

		BaseResponse baseResponse = response.readEntity(BaseResponse.class);

		LOG.warn("Failed to list DNS records for zone {}, type {} and name {}; {}", zone, type, name, baseResponse);
		return null;
	}

	/**
	 * Update a DNS record with a new address
	 * 
	 * @param dnsRecord
	 *            the record
	 * @param content
	 *            the address
	 * @return true on success, otherwise false
	 */
	public boolean updateDnsRecord(final DnsRecord dnsRecord, final String content) {
		UpdateDnsRecord updateDnsRecord = dnsRecord.toUpdateDnsRecord();
		updateDnsRecord.setContent(content);

		WebTarget target = this.getTarget().path("zones").path(dnsRecord.getZoneId()).path("dns_records")
				.path(dnsRecord.getId());

		Response response = target.request().put(Entity.entity(updateDnsRecord, MediaType.APPLICATION_JSON));

		BaseResponse baseResponse = response.readEntity(BaseResponse.class);

		if (response.getStatus() == 200) {
			LOG.info("Updated DNS record {} with content {}", dnsRecord, content);
			return baseResponse.isSuccess();
		}

		LOG.warn("Failed to update DNS record {} with content {}; {}", dnsRecord, content, baseResponse);
		return false;
	}

}
