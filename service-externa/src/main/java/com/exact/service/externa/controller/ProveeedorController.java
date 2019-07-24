package com.exact.service.externa.controller;

import java.io.IOException;

import org.json.JSONException;
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

import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/proveedores")
public class ProveeedorController {

	@Autowired
	IProveedorService proveedorService;
	
	@Autowired
	IPlazoDistribucionService plazoDistribucionService;
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<Proveedor>> listarProveedoresActivos() throws IOException, JSONException {
		return new ResponseEntity<>(proveedorService.listarProveedoresActivos(),HttpStatus.OK);
	}
	
	@GetMapping("{id}/plazosdistribucion")
	public ResponseEntity<String> listarPlazosDistribucionPorProveedor(@PathVariable Long id) throws IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(plazoDistribucionService.listarByProveedorId(id));
	    return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Proveedor> guardar(@RequestBody Proveedor proveedor) {
		proveedor.setActivo(true);
		try {
			return new ResponseEntity<>(proveedorService.guardar(proveedor), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Proveedor> modificar(@PathVariable Long id, @RequestBody Proveedor proveedor) {
		proveedor.setId(id);
		try {
			return new ResponseEntity<>(proveedorService.modificar(proveedor), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Proveedor>> listarAll() throws IOException, JSONException {
		return new ResponseEntity<>(proveedorService.listarProveedores(),HttpStatus.OK);
	}
	
	@GetMapping("{id}/proveedorplazo")
	public ResponseEntity<Iterable<Proveedor>> listarProveedorByPlazo(@PathVariable Long id) throws IOException, JSONException {
		return new ResponseEntity<>(proveedorService.buscarProveedorByPlazoId(id),HttpStatus.OK);
	}
	
}
