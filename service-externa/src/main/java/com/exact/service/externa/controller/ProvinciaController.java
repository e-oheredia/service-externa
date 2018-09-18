package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IDistritoService;
import com.exact.service.externa.service.interfaces.IProvinciaService;

@RestController
@RequestMapping("provincias")
public class ProvinciaController {
	
	@Autowired
	IDistritoService distritoService;
	
	@Autowired
	IProvinciaService provinciaService;
	
	@GetMapping("/{id}/distritos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarDistritosByProvinciaId(@PathVariable("id") Long id) throws ClientProtocolException, IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String,Object>>>(distritoService.listarDistritosByIdProvincia(id), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll() throws ClientProtocolException, IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String,Object>>>(provinciaService.listarAll(), HttpStatus.OK);
	}
	
}
