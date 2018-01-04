package com.ovh.api.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.ovh.api.OvhApiEndpoints;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.gson.Gson;
import com.ovh.api.OvhApi;

@RunWith(PowerMockRunner.class)
@PrepareForTest({OvhApi.class, System.class})
public class ApiTest {
	
	HttpURLConnection mockCon;
	
	String me = "{\"firstname\":\"Foo\",\"vat\":\"\",\"ovhSubsidiary\":\"FR\",\"area\":\"\",\"birthDay\":\"Invalid date\",\"nationalIdentificationNumber\":null,\"spareEmail\":null,\"ovhCompany\":\"ovh\",\"state\":\"complete\",\"email\":\"test@foobar.com\",\"currency\":{\"symbol\":\"€\",\"code\":\"EUR\"},\"city\":\"Roubaix\",\"fax\":\"\",\"nichandle\":\"fb0000-ovh\",\"address\":\"1 rue du Foobar\",\"companyNationalIdentificationNumber\":null,\"birthCity\":\"\",\"country\":\"FR\",\"language\":\"fr_FR\",\"organisation\":\"\",\"name\":\"Bar\",\"phone\":\"+33.000000000\",\"sex\":\"male\",\"zip\":\"59000\",\"corporationType\":\"\",\"legalform\":\"individual\"}";
	
	@Before
	public void setup() throws Exception {
		mockCon = Mockito.mock(HttpURLConnection.class);
		
		URL mockedUrl = PowerMockito.mock(URL.class);
		PowerMockito.whenNew(URL.class).withArguments(Mockito.anyString()).thenReturn(mockedUrl);
		PowerMockito.when(mockedUrl.openConnection()).thenReturn(mockCon);
	}
	
	private void setupResp(String resp, int respCode) throws Exception {
		InputStream inputStrm = new ByteArrayInputStream(resp.getBytes(StandardCharsets.UTF_8));
		Mockito.when(mockCon.getInputStream()).thenReturn(inputStrm);
		
		Mockito.when(mockCon.getResponseCode()).thenReturn(respCode);
	}
	
	@Test
	public void me() throws Exception {
		OvhApiEndpoints endpoint = OvhApiEndpoints.OVH_EU;
		String appKey = "000000000000000";
		String appSecret = "00000000000000000000000000000000";
		String consumerKey = "00000000000000000000000000000000";
		OvhApi api = new OvhApi(endpoint, appKey, appSecret, consumerKey);
		
		setupResp(me,200);
		
		String json = api.get("/me");
		Gson gson = new Gson();
		Me me = gson.fromJson(json, Me.class);
		
		Assert.assertEquals(me.firstname, "Foo");
		Assert.assertEquals(me.name, "Bar");
		Assert.assertEquals(me.nichandle, "fb0000-ovh");
	}

	public class Me {
		public String firstname;
		public String name;
		public String nichandle;
		
	}
	
}
