package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.exact.service.externa.entity.TipoPlazoDistribucion;
import com.exact.service.externa.service.interfaces.ITipoPlazoDistribucionService;

@RestController
@RequestMapping("/tiposplazo")
public class TipoPlazoDistribucionController {

	@Autowired
	ITipoPlazoDistribucionService tipoPlazoService;
	
	@GetMapping
	public ResponseEntity<Iterable<TipoPlazoDistribucion>> listarAll(){
		return new ResponseEntity<Iterable<TipoPlazoDistribucion>>(tipoPlazoService.listarAll(), HttpStatus.OK);
	}
}
