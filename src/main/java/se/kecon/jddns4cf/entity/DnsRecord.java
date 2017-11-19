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

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * DNS record
 * 
 * @author Kenny Colliander Nordin
 */
public class DnsRecord extends BaseEntity {
	private String type;

	private String name;

	private String content;

	private int ttl;

	private boolean proxiable;

	private boolean proxied;

	private boolean locked;

	private String zoneId;

	private String zoneName;

	public UpdateDnsRecord toUpdateDnsRecord() {
		final UpdateDnsRecord record = new UpdateDnsRecord();

		record.setType(type);
		record.setName(name);
		record.setContent(content);
		record.setProxied(proxied);
		record.setTtl(ttl);

		return record;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

	public boolean isProxiable() {
		return proxiable;
	}

	public void setProxiable(boolean proxiable) {
		this.proxiable = proxiable;
	}

	public boolean isProxied() {
		return proxied;
	}

	public void setProxied(boolean proxied) {
		this.proxied = proxied;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public String getZoneId() {
		return zoneId;
	}

	@JsonProperty(value = "zone_id")
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	@JsonProperty(value = "zone_name")
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	@Override
	public String toString() {
		return "DnsRecord [type=" + type + ", name=" + name + ", content=" + content + ", ttl=" + ttl + ", proxiable="
				+ proxiable + ", proxied=" + proxied + ", locked=" + locked + ", zoneId=" + zoneId + ", zoneName="
				+ zoneName + ", id=" + getId() + ", createdOn=" + getCreatedOn() + ", modifiedOn=" + getModifiedOn()
				+ "]";
	}
}
