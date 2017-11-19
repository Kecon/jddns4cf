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
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import se.kecon.jddns4cf.entity.DnsRecord;

/**
 * Main class for the application.
 * 
 * @author Kenny Colliander Nordin
 */
public class App {
	private static Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * 
	 * @param args
	 *            the program arguments. Either config.json file or -i (to print
	 *            interface names) as first argument.
	 * @throws Exception
	 *             poor error handling
	 */
	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.out.println("jddns4cf [config path]");
			System.out.println("  -i    print available interface names");
			return;
		}

		if (args[0].equals("-i")) {
			printInterfaceNames();
		} else {
			Config config = loadConfig(Paths.get(args[0]));
			synchronizeIp(config);
		}
	}

	/**
	 * Synchronizde the CF DNS record with the address on the gateway
	 * 
	 * @param config
	 *            the configuration
	 * @throws SocketException
	 */
	private static void synchronizeIp(final Config config) throws SocketException {

		final CfApi cfApi = new CfApi(config.getAuthEmail(), config.getAuthKey());
		final Class<?> addressType = getAddressType(config);
		DnsRecord dnsRecord = null;
		LOG.info("Synchronize using {}", config.getInterfaceName());
		while (true) {
			if (dnsRecord == null) {
				final DnsRecord[] dnsRecords = cfApi.listDnsRecords(config.getZoneId(), config.getType(),
						config.getName());

				if (dnsRecords == null || dnsRecords.length != 1) {
					LOG.warn("Invalid amount of records returned; must only be 1 record");
				} else {
					dnsRecord = dnsRecords[0];
					LOG.trace("Retrieved {}", dnsRecord);
				}
			}

			final String newAddress = getIp(config.getInterfaceName(), addressType);

			if (newAddress == null) {
				LOG.warn("No address found for {}", config.getInterfaceName());
			} else if (dnsRecord != null && !newAddress.equals(dnsRecord.getContent())) {
				if (cfApi.updateDnsRecord(dnsRecord, newAddress)) {
					dnsRecord = null;
				}
			}

			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	/**
	 * Get the address type of the record that should be updated
	 * 
	 * @param config
	 *            the config
	 * @return the class for the address type, either AAAA or default A
	 */
	private static Class<?> getAddressType(final Config config) {
		if ("AAAA".equals(config.getType())) {
			return Inet6Address.class;
		}

		return Inet4Address.class;
	}

	/**
	 * Get the first available IP address that is assigned for a specific
	 * interface of a specific type
	 * 
	 * @param interfaceName
	 *            the interface name
	 * @param type
	 *            the type of address, either IPv4 or IPv6
	 * @return the IP or null if no address was found
	 * @throws SocketException
	 */
	private static String getIp(final String interfaceName, final Class<?> type) throws SocketException {
		final Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

		for (final NetworkInterface networkInterface : Collections.list(nets)) {
			if (interfaceName.equals(networkInterface.getDisplayName())) {
				for (final InetAddress inetAddress : Collections.list(networkInterface.getInetAddresses())) {
					if (inetAddress.getClass() == type) {
						return inetAddress.getHostAddress();
					}
				}
			}
		}

		LOG.warn("Failed to retrieve address for interface {}", interfaceName);
		return null;
	}

	/**
	 * Print all available interface names
	 * 
	 * @throws SocketException
	 */
	private static void printInterfaceNames() throws SocketException {
		final Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();

		for (NetworkInterface networkInterface : Collections.list(nets)) {
			System.out.println(networkInterface.getDisplayName());
		}
	}

	/**
	 * Load the configuration
	 * 
	 * @param path
	 *            the path to the configuration
	 * @return the configuration
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private static Config loadConfig(final Path path) throws JsonParseException, JsonMappingException, IOException {
		final ObjectMapper mapper = new ObjectMapper().configure(Feature.AUTO_CLOSE_SOURCE, true)
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		try (final InputStream inputStream = Files.newInputStream(path)) {
			return mapper.readValue(inputStream, Config.class);
		}
	}
}
