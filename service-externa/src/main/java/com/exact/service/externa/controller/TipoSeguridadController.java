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

import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.service.interfaces.ITipoSeguridadService;

@RestController
@RequestMapping("/tiposseguridad")
public class TipoSeguridadController {
	
	@Autowired
	ITipoSeguridadService tipoSeguridadService;
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<TipoSeguridad>> listarTipoSeguridadActivos(){
		return new ResponseEntity<Iterable<TipoSeguridad>>(tipoSeguridadService.listarTipoSeguridadActivos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<TipoSeguridad> guardar(@RequestBody TipoSeguridad tipoSeguridad){
		tipoSeguridad.setActivo(true);
		return new ResponseEntity<TipoSeguridad>(tipoSeguridadService.guardar(tipoSeguridad), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TipoSeguridad> modificar(@PathVariable Long id, @RequestBody TipoSeguridad tipoSeguridad){
		tipoSeguridad.setId(id);
		return new ResponseEntity<TipoSeguridad>(tipoSeguridadService.guardar(tipoSeguridad), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<TipoSeguridad>> listarAll(){
		return new ResponseEntity<Iterable<TipoSeguridad>>(tipoSeguridadService.listarAll(), HttpStatus.OK);
	}
	
}
