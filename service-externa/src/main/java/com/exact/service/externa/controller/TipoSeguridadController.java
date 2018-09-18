package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.service.interfaces.ITipoSeguridadService;

@RestController
@RequestMapping("/tiposseguridad")
public class TipoSeguridadController {
	
	@Autowired
	ITipoSeguridadService tipoSeguridadService;
	
	@GetMapping
	public ResponseEntity<Iterable<TipoSeguridad>> listarAll(){
		return new ResponseEntity<Iterable<TipoSeguridad>>(tipoSeguridadService.listarAll(), HttpStatus.OK);
	}
	
}
