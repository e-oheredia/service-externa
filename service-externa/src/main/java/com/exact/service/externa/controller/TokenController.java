package com.exact.service.externa.controller;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;


@RestController
@RequestMapping("/token")
public class TokenController {
	
	
	@Value("${token.acceso}")
	private String tokenacceso;
	
	@Autowired
	private IRequester requester;
	
	
	@GetMapping
	public ResponseEntity<String> getToken (@RequestHeader("Authorization") String header) throws Exception{
		
		
		//RestTemplate restTemplate = new RestTemplate();
		HttpGet httpGet = new HttpGet(tokenacceso);
		httpGet.setHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		
		
		
		//CommonUtils.jsonArrayToMap(responseJson);
		
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
		
		
		
		//ResponseEntity<Map<String, Object>> rpta ;
		
		
		
		
	}

}
