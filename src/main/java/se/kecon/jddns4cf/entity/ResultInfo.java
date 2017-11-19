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
 * Result info
 * 
 * @author Kenny Colliander Nordin
 */
public class ResultInfo {
	private int page;

	private int perPage;

	private int count;

	private int totalCount;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPage() {
		return perPage;
	}

	@JsonProperty(value = "per_page")
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getTotalCount() {
		return totalCount;
	}

	@JsonProperty(value = "total_count")
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "ResultInfo [page=" + page + ", perPage=" + perPage + ", count=" + count + ", totalCount=" + totalCount
				+ "]";
	}
}
