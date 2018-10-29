package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.service.interfaces.IEstadoDocumentoService;

@RestController
@RequestMapping("/estadosdocumento")
public class EstadoDocumentoController {
	
	@Autowired
	IEstadoDocumentoService estadoDocumentoService;
	
	@GetMapping
	public ResponseEntity<Iterable<EstadoDocumento>> listarAll() {
		return new ResponseEntity<Iterable<EstadoDocumento>>(estadoDocumentoService.listarAll(), HttpStatus.OK);
	}
}
