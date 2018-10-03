package com.exact.service.externa.controller;




import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.exact.service.externa.request.IRequester;



@RestController
@RequestMapping("/token")
public class TokenController {
	
	
	@Value("${token.acceso}")
	private String tokenacceso;
	
	@Autowired
	private IRequester requester;
	
	
	@GetMapping
	public ResponseEntity<String> getToken (@RequestHeader("Authorization") String header) throws Exception{
		
		HttpGet httpGet = new HttpGet(tokenacceso);
		httpGet.setHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		
		return new ResponseEntity<String>(response, HttpStatus.OK);
			
	}

}
