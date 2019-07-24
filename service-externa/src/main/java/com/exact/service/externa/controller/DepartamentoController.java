package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IDepartamentoService;
import com.exact.service.externa.service.interfaces.IProvinciaService;

@RestController
@RequestMapping("departamentos")
public class DepartamentoController {
	
	@Autowired
	IProvinciaService provinciaService;
	
	@Autowired 
	IDepartamentoService departamentoService;
	
	@GetMapping("/{id}/provincias")
	public ResponseEntity<Iterable<Map<String, Object>>> listarProvinciaByDepartamentoId(@PathVariable("id") Long id) throws IOException, JSONException{
		return new ResponseEntity<>(provinciaService.listarProvinciasByDepartamentoId(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll() throws IOException, JSONException{
		return new ResponseEntity<>(departamentoService.listarAll(), HttpStatus.OK);
	}
	
}
