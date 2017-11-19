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

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Base response
 * 
 * @author Kenny Colliander Nordin
 */
public class BaseResponse {
	private boolean success;

	private Error[] errors;

	private ResultInfo resultInfo;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public Error[] getErrors() {
		return errors;
	}

	public void setErrors(Error[] errors) {
		this.errors = errors;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	@JsonProperty(value = "result_info")
	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

	@Override
	public String toString() {
		return "BaseResponse [success=" + success + ", errors=" + Arrays.toString(errors) + ", resultInfo=" + resultInfo
				+ "]";
	}

}
