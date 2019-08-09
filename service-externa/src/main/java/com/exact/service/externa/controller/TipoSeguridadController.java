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

import com.exact.service.externa.converter.convertertiposeguridad;
import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.model.MTipoSeguridad;
import com.exact.service.externa.service.interfaces.ITipoSeguridadService;

@RestController
@RequestMapping("/tiposseguridad")
public class TipoSeguridadController {
	
	@Autowired
	ITipoSeguridadService tipoSeguridadService;
	
	@Autowired
	convertertiposeguridad converter;
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<MTipoSeguridad>> listarTipoSeguridadActivos(){
		return new ResponseEntity<Iterable<MTipoSeguridad>>(tipoSeguridadService.listarTipoSeguridadActivos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<MTipoSeguridad> guardar(@RequestBody TipoSeguridad  tipoSeguridad){
		tipoSeguridad.setActivo(true);
		try {
			return new ResponseEntity<MTipoSeguridad>(tipoSeguridadService.guardar(tipoSeguridad), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MTipoSeguridad>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<MTipoSeguridad> modificar(@PathVariable Long id, @RequestBody TipoSeguridad tipoSeguridad){
		tipoSeguridad.setId(id);
		try {
			return new ResponseEntity<MTipoSeguridad>(tipoSeguridadService.guardar(tipoSeguridad), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<MTipoSeguridad>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<Iterable<TipoSeguridad>> listarAll(){
		return new ResponseEntity<Iterable<TipoSeguridad>>(tipoSeguridadService.listarAll(), HttpStatus.OK);
	}
	
}
