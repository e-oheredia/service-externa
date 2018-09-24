package com.exact.service.externa.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.service.classes.DocumentoService;

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
	
}
