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

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

/**
 * Client request filter which add authentication headers
 * 
 * @author Kenny Colliander Nordin
 */
public final class CfAuthenticator implements ClientRequestFilter {

	private final String authKey;

	private final String authEmail;

	public CfAuthenticator(final String authEmail, final String authKey) {
		super();
		this.authKey = authKey;
		this.authEmail = authEmail;
	}

	public void filter(final ClientRequestContext clientRequestContext) throws IOException {
		clientRequestContext.getHeaders().add("X-Auth-Key", authKey);
		clientRequestContext.getHeaders().add("X-Auth-Email", authEmail);
	}

}
