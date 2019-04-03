package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

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

import com.exact.service.externa.service.interfaces.IProductoService;


@RestController
@RequestMapping("/productos")
public class ProductoController {
	
	@Autowired
	IProductoService productoService;
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll() throws IOException, JSONException, Exception {
		return new ResponseEntity<Iterable<Map<String, Object>>>(productoService.listarAll(), HttpStatus.OK);
	}
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarActivos() throws IOException, JSONException, Exception {
		return new ResponseEntity<Iterable<Map<String, Object>>>(productoService.listarActivos(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardar(@RequestBody String producto) throws IOException, JSONException, Exception  {
		return new ResponseEntity<Map<String, Object>>(productoService.guardar(producto), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id,@RequestBody String producto) throws IOException, JSONException, Exception {
		return new ResponseEntity<Map<String, Object>>(productoService.modificar(id, producto), HttpStatus.OK);

	}
	

}
