package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.service.interfaces.IEnvioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/envios")
public class EnvioController {

	@Autowired
	IEnvioService envioService;

	@Secured("ROLE_CREADOR_DOCUMENTO")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<? extends Object> registrarEnvio(@RequestParam("envio") String envioJsonString, @RequestParam(required=false) MultipartFile file,Authentication authentication) throws IOException, JSONException{
		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		Envio envio = mapper.readValue(envioJsonString, Envio.class);		
		
		return new ResponseEntity<Envio>(
				envioService.registrarEnvio(envio,Long.valueOf(datosUsuario.get("idUsuario").toString()), file), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/autorizacion")
	public ResponseEntity<Envio> autorizarEnvio(@PathVariable Long id, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();		
		return new ResponseEntity<Envio>(
				envioService.autorizarEnvio(id,Long.valueOf(datosUsuario.get("idUsuario").toString())), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/denegacion")
	public ResponseEntity<Envio> denegarEnvio(@PathVariable Long id, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();		
		return new ResponseEntity<Envio>(
				envioService.denegarEnvio(id,Long.valueOf(datosUsuario.get("idUsuario").toString())), HttpStatus.OK);
	}
	
	@GetMapping("/noautorizados")
	public ResponseEntity<String> listarEnviosNoAutorizados() throws ClientProtocolException, IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(envioService.listarEnviosNoAutorizados());
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarEnviosCreados() throws ClientProtocolException, IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(envioService.listarEnviosCreados());
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	

}