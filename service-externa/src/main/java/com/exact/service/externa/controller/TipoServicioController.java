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
import com.exact.service.externa.entity.TipoServicio;
import com.exact.service.externa.service.interfaces.ITipoServicioService;

@RestController
@RequestMapping("/tiposservicio")
public class TipoServicioController {
	@Autowired
	ITipoServicioService tipoServicioService;
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<TipoServicio>> listarTipoServicioActivos(){
		return new ResponseEntity<Iterable<TipoServicio>>(tipoServicioService.listarTipoServicioActivos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<TipoServicio> guardar(@RequestBody TipoServicio tipoServicio){
		tipoServicio.setActivo(true);
		return new ResponseEntity<TipoServicio>(tipoServicioService.guardar(tipoServicio), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<TipoServicio> modificar(@PathVariable Long id, @RequestBody TipoServicio tipoServicio){
		tipoServicio.setId(id);
		return new ResponseEntity<TipoServicio>(tipoServicioService.guardar(tipoServicio), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<TipoServicio>> listarAll() {
		return new ResponseEntity<Iterable<TipoServicio>>(tipoServicioService.listarAll(), HttpStatus.OK);
	}
	
}
