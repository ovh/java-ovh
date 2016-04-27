package com.ovh.api.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
	
	@Test
	public void raw() throws Exception {
		OvhApi api = new OvhApi("https://foo.bar", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://foo.bar/me");
	}

	
	@Test
	public void ovhEu() throws Exception {
		OvhApi api = new OvhApi("ovh-eu", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.ovh.com/1.0/me");
	}
	
	
	@Test
	public void ovhCa() throws Exception {
		OvhApi api = new OvhApi("ovh-ca", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.ovh.com/1.0/me");
	}
	
	@Test
	public void kimsufiEu() throws Exception {
		OvhApi api = new OvhApi("kimsufi-eu", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.kimsufi.com/1.0/me");
	}
	
	@Test
	public void kimsufiCa() throws Exception {
		OvhApi api = new OvhApi("kimsufi-ca", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.kimsufi.com/1.0/me");
	}
	
	@Test
	public void soyoustartEu() throws Exception {
		OvhApi api = new OvhApi("soyoustart-eu", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://eu.api.soyoustart.com/1.0/me");
	}
	
	@Test
	public void soyoustartCa() throws Exception {
		OvhApi api = new OvhApi("soyoustart-ca", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://ca.api.soyoustart.com/1.0/me");
	}
	
	@Test
	public void runabove() throws Exception {
		OvhApi api = new OvhApi("runabove", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://api.runabove.com/1.0/me");
	}
	
	@Test
	public void runaboveCa() throws Exception {
		OvhApi api = new OvhApi("runabove-ca", "", "", "");
		api.get("/me");
		PowerMockito.verifyNew(URL.class).withArguments("https://api.runabove.com/1.0/me");
	}
	
}
