package com.ovh.api.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.ovh.api.OvhApiEndpoints;
import com.ovh.api.OvhApiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ovh.api.OvhApi;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OvhApi.class)
public class EndpointsTest {
	
	@Before
	public void setup() throws Exception {
		HttpURLConnection mockCon = Mockito.mock(HttpURLConnection.class);
		InputStream inputStrm = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));
		Mockito.when(mockCon.getInputStream()).thenReturn(inputStrm);
		Mockito.when(mockCon.getResponseCode()).thenReturn(200);
		
		URL mockedUrl = PowerMockito.mock(URL.class);
		PowerMockito.whenNew(URL.class).withArguments(Mockito.anyString()).thenReturn(mockedUrl);
		PowerMockito.when(mockedUrl.openConnection()).thenReturn(mockCon);
	}
	
	@Test(expected = OvhApiException.class)
	public void raw() throws Exception {
		OvhApi api = new OvhApi(null, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://foo.bar/me");
	}

	
	@Test
	public void ovhEu() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.OVH_EU, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.ovh.com/1.0/me");
	}
	
	
	@Test
	public void ovhCa() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.OVH_CA, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.ovh.com/1.0/me");
	}
	
	@Test
	public void kimsufiEu() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.KIMSUFI_EU, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.kimsufi.com/1.0/me");
	}
	
	@Test
	public void kimsufiCa() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.KIMSUFI_CA, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.kimsufi.com/1.0/me");
	}
	
	@Test
	public void soyoustartEu() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.SOYOUSTART_EU, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.soyoustart.com/1.0/me");
	}
	
	@Test
	public void soyoustartCa() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.SOYOUSTART_CA, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.soyoustart.com/1.0/me");
	}
	
	@Test
	public void runabove() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.RUNABOVE, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://api.runabove.com/1.0/me");
	}
	
	@Test
	public void runaboveCa() throws Exception {
		OvhApi api = new OvhApi(OvhApiEndpoints.RUNAVOVE_CA, "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://api.runabove.com/1.0/me");
	}
	
}
