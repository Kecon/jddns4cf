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

/**
 * Request for updating DNS record
 * 
 * @author Kenny Colliander Nordin
 */
public class UpdateDnsRecord {
	private String type;

	private String name;

	private String content;

	private Integer ttl;

	private Boolean proxied;

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

	public Integer getTtl() {
		return ttl;
	}

	public void setTtl(Integer ttl) {
		this.ttl = ttl;
	}

	public Boolean getProxied() {
		return proxied;
	}

	public void setProxied(Boolean proxied) {
		this.proxied = proxied;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((proxied == null) ? 0 : proxied.hashCode());
		result = prime * result + ((ttl == null) ? 0 : ttl.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateDnsRecord other = (UpdateDnsRecord) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (proxied == null) {
			if (other.proxied != null)
				return false;
		} else if (!proxied.equals(other.proxied))
			return false;
		if (ttl == null) {
			if (other.ttl != null)
				return false;
		} else if (!ttl.equals(other.ttl))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpdateDnsRecord [type=" + type + ", name=" + name + ", content=" + content + ", ttl=" + ttl
				+ ", proxied=" + proxied + "]";
	}
}
