package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.TipoServicio;
import com.exact.service.externa.service.interfaces.ITipoServicioService;

@RestController
@RequestMapping("/tiposservicio")
public class TipoServicioController {
	@Autowired
	ITipoServicioService tipoServicioService;
	
	@GetMapping
	public ResponseEntity<Iterable<TipoServicio>> listarAll() {
		return new ResponseEntity<Iterable<TipoServicio>>(tipoServicioService.listarAll(), HttpStatus.OK);
	}
	
}
