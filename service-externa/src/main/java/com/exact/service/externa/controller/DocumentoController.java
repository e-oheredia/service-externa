package com.exact.service.externa.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.classes.DocumentoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@EnableGlobalMethodSecurity(securedEnabled=true)
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	
	@Autowired
	private DocumentoService documentoService;
	
	@PutMapping("/custodia")
	public ResponseEntity<Integer> custodiarDocumentos(@RequestBody List<Documento> documentos, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		return new ResponseEntity<Integer>(
				documentoService.custodiarDocumentos(documentos, Long.valueOf(datosUsuario.get("idUsuario").toString())), 
				HttpStatus.OK);
	}
	
	@PostMapping("/porcrearguia")
	public ResponseEntity<String> listarDocumentosGuiaPorCrear(@RequestBody Guia guia) throws ClientProtocolException, IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(documentoService.listarDocumentosGuiaPorCrear(guia));
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/custodiados")
	public ResponseEntity<String> listarDocumentosCustodiados() throws ClientProtocolException, IOException, JSONException {
		
		SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("envioFilter", SimpleBeanPropertyFilter.serializeAllExcept("documentos")); 
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.setFilterProvider(filterProvider); 	
		
	    String dtoMapAsString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(documentoService.listarDocumentosPorEstado());
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	
	}
	
}
