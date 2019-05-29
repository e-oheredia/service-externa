package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.TipoDevolucion;
import com.exact.service.externa.service.interfaces.ITipoDevolucionService;

@RestController
@RequestMapping("/tipodevolucion")
public class TipoDevolucionController {

	@Autowired
	ITipoDevolucionService tipodevolucionService;
	
	@GetMapping
	public ResponseEntity<Iterable<TipoDevolucion>> listarAll(){
		return new ResponseEntity<Iterable<TipoDevolucion>>(tipodevolucionService.listarAll(), HttpStatus.OK);
	}
}
