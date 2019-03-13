package com.exact.service.externa.controller;

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

import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;

@RestController
@RequestMapping("/plazosdistribucion")
public class PlazoDistribucionController {
	
	@Autowired
	IPlazoDistribucionService plazoDistribucionService;
	
	@GetMapping
	public ResponseEntity<Iterable<PlazoDistribucion>> listarAll() {
		return new ResponseEntity<Iterable<PlazoDistribucion>>(plazoDistribucionService.listarAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<PlazoDistribucion> guardar(@RequestBody PlazoDistribucion plazoDist) {
		plazoDist.setActivo(true);
		return new ResponseEntity<PlazoDistribucion>(plazoDistribucionService.guardar(plazoDist), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PlazoDistribucion> modificar(@PathVariable Long id, @RequestBody PlazoDistribucion plazoDist) {
		plazoDist.setId(id);
		return new ResponseEntity<PlazoDistribucion>(plazoDistribucionService.guardar(plazoDist), HttpStatus.OK);
	}
	
}
