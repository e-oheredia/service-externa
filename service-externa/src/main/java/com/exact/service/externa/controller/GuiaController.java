package com.exact.service.externa.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IGuiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	IGuiaService guiaService;
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarGuiasCreados() throws ClientProtocolException, IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(guiaService.listarGuiasCreadas());
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
}
