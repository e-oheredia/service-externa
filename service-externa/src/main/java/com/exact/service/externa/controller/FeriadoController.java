package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IFeriadoService;

@RestController
@RequestMapping("/feriados")
public class FeriadoController {
	
	@Autowired
	IFeriadoService feriadoservice;
	
	private static final Log Logger = LogFactory.getLog(FeriadoController.class);

	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable Long id) throws io.jsonwebtoken.io.IOException, Exception{
		Logger.info("ddaas");
		//Map<String, Object> respuesta = new HashMap<String, Object>();
		
		Map<String,Object> Ambito = feriadoservice.eliminar(id);
		
		int rpta = (int) Ambito.get("responsecode");
		
		if(rpta==400) {
			return new ResponseEntity<Map<String, Object>>(Ambito, HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Map<String, Object>>(Ambito, HttpStatus.OK);
		} 
	}
	
	
	
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarferiados() throws IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String, Object>>>(feriadoservice.listarferiados(), HttpStatus.OK);
	} 
	
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardar(@RequestBody String feriado) throws io.jsonwebtoken.io.IOException, Exception{
		
		Map<String,Object> Ambito = feriadoservice.guardarferiados(feriado);
		
		
		int rpta = (int) Ambito.get("responsecode");
		
		
		if(rpta==400) {
			return new ResponseEntity<Map<String, Object>>(Ambito, HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Map<String, Object>>(Ambito, HttpStatus.OK);
		} 
		
		
	}
	


}
