package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.ITipoDocumentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/tiposdocumento")
public class TipoDocumentoController {
	
	@Autowired
	ITipoDocumentoService tipoDocumentoService;
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll() throws IOException, JSONException {
		return new ResponseEntity<Iterable<Map<String,Object>>>(tipoDocumentoService.listarAll(), HttpStatus.OK);
	}
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarActivos() throws IOException, JSONException {
		return new ResponseEntity<Iterable<Map<String,Object>>>(tipoDocumentoService.listarActivos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> guardar(@RequestBody String tipoDocumento) throws ClientProtocolException, IOException, JSONException  {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			Map<String,Object> tipodocumento = tipoDocumentoService.guardar(tipoDocumento);
			return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tipodocumento), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> modificar(@PathVariable Long id,@RequestBody String tipoDocumento) throws IOException, JSONException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			Map<String,Object> tipodocumento = tipoDocumentoService.modificar(id, tipoDocumento);
			return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(tipodocumento), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
