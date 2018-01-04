package com.ovh.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.ovh.api.OvhApiException.OvhApiExceptionCause;

/**
 * Simple low level wrapper over the OVH REST API.
 * 
 * 
 * @author mbsk
 *
 */
public class OvhApi {
	
	private final OvhApiEndpoints endpoint;
	private final String appKey;
	private final String appSecret;
	private final String consumerKey;

	public OvhApi() throws OvhApiException {
		Map<String, String> env = System.getenv();
		if (env.containsKey("OVH_ENDPOINT") && env.containsKey("OVH_APPLICATION_KEY") && env.containsKey("OVH_APPLICATION_SECRET") && env.containsKey("OVH_CONSUMER_KEY")) {
			endpoint = OvhApiEndpoints.fromString(System.getenv("OVH_ENDPOINT"));
			appKey = System.getenv("OVH_APPLICATION_KEY");
			appSecret = System.getenv("OVH_APPLICATION_SECRET");
			consumerKey = System.getenv("OVH_CONSUMER_KEY");

			assertAllConfigNotNull();
			return;
		}

		// find the config file
		File configFile = Utils.getConfigFile("ovh.conf", System.getProperty("user.home") + "/ovh.conf", "/etc/ovh.conf");
		if (configFile == null) {
			throw new OvhApiException("environment variables OVH_ENDPOINT, OVH_APPLICATION_KEY, OVH_APPLICATION_SECRET, OVH_CONSUMER_KEY or configuration files ./ovh.conf, ~/ovh.conf, /etc/ovh.conf were not found", OvhApiExceptionCause.CONFIG_ERROR);
		}

		try {
			// read the configuration file
			Properties config = new Properties();
			config.load(new FileInputStream(configFile));

			// get the values
			endpoint = OvhApiEndpoints.fromString(config.getProperty("endpoint", null));
			appKey = config.getProperty("application_key", null);
			appSecret = config.getProperty("application_secret", null);
			consumerKey = config.getProperty("consumer_key", null);

			assertAllConfigNotNull();
		} catch (Exception e) {
			throw new OvhApiException(e.getMessage(), OvhApiExceptionCause.CONFIG_ERROR);
		}
	}

	public OvhApi(OvhApiEndpoints endpoint, String appKey, String appSecret, String consumerKey) {
		this.endpoint = endpoint;
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.consumerKey = consumerKey;

		assertAllConfigNotNull();
	}

	private void assertAllConfigNotNull() {
		if (endpoint == null || appKey == null || appSecret == null || consumerKey == null) {
			throw new IllegalArgumentException("Constructor parameters cannot be null");
		}
	}

	public String get(String path) throws OvhApiException {
		return get(path, "", true);
	}

	public String get(String path, boolean needAuth) throws OvhApiException {
		return get(path, "", needAuth);
	}

	public String get(String path, String body, boolean needAuth) throws OvhApiException {
		return call("GET", body, path, needAuth);
	}

	public String put(String path, String body, boolean needAuth) throws OvhApiException {
		return call("PUT", body, path, needAuth);
	}

	public String post(String path, String body, boolean needAuth) throws OvhApiException {
		return call("POST", body, path, needAuth);
	}

	public String delete(String path, String body, boolean needAuth) throws OvhApiException {
		return call("DELETE", body, path, needAuth);
	}

	private String call(String method, String body, String path, boolean needAuth) throws OvhApiException {
		String urlStr = endpoint.getUrl() + path;

		Map<String, String> headers = new HashMap<>();
		headers.put("Content-Type", "application/json");
		headers.put("X-Ovh-Application", appKey);

		// handle authentication
		if (needAuth) {
			// get timestamp from local system
			long timestamp = System.currentTimeMillis() / 1000;

			// build signature
			String toSign = appSecret + "+" + consumerKey + "+" + method + "+" + urlStr + "+" + body + "+" + timestamp;
			String signature;
			try {
				signature = "$1$" + Utils.sha1Hex(toSign);
			} catch (NoSuchAlgorithmException | IOException e) {
				throw new OvhApiException(e.getMessage(), OvhApiExceptionCause.INTERNAL_ERROR);
			}

			// set HTTP headers for authentication
			headers.put("X-Ovh-Consumer", consumerKey);
			headers.put("X-Ovh-Signature", signature);
			headers.put("X-Ovh-Timestamp", Long.toString(timestamp));
		}

		return execRequest(method, urlStr, headers, body);
	}

	private static String execRequest(String method, String urlStr, Map<String, String> headers, String body) throws OvhApiException {
		try {
			// prepare
			HttpURLConnection request = (HttpURLConnection) new URL(urlStr).openConnection();
			request.setRequestMethod(method);
			request.setReadTimeout(30000);
			request.setConnectTimeout(30000);

			if (headers != null) {
				for (Map.Entry<String, String> header: headers.entrySet()) {
					request.setRequestProperty(header.getKey(), header.getValue());
				}
			}

			if (body != null && !body.isEmpty()) {
				request.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(request.getOutputStream());
				out.writeBytes(body);
				out.flush();
				out.close();
			}

			int responseCode = request.getResponseCode();
			BufferedReader in = new BufferedReader(new InputStreamReader(responseCode == 200 ? request.getInputStream() : request.getErrorStream()));

			// build response
			StringBuilder response = new StringBuilder();
			for (String inputLine; (inputLine = in.readLine()) != null;) {
				response.append(inputLine);
			}

			in.close();

			if (responseCode == 200) {
				// return the raw JSON result
				return response.toString();
			} else if (responseCode == 400) {
				throw new OvhApiException(response.toString(), OvhApiExceptionCause.BAD_PARAMETERS_ERROR);
			} else if (responseCode == 403) {
				throw new OvhApiException(response.toString(), OvhApiExceptionCause.AUTH_ERROR);
			} else if (responseCode == 404) {
				throw new OvhApiException(response.toString(), OvhApiExceptionCause.RESOURCE_NOT_FOUND);
			} else if (responseCode == 409) {
				throw new OvhApiException(response.toString(), OvhApiExceptionCause.RESOURCE_CONFLICT_ERROR);
			} else {
				throw new OvhApiException(response.toString(), OvhApiExceptionCause.API_ERROR);
			}
		} catch (IOException e) {
			throw new OvhApiException(e.getMessage(), OvhApiExceptionCause.INTERNAL_ERROR);
		}
	}
}
