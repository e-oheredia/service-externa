package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IPlazosService;

@RestController
@RequestMapping("/plazos")
public class PlazosController {

	@Autowired
	IPlazosService plazosService;
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarPlazos() throws IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String, Object>>>(plazosService.listarAll(), HttpStatus.OK);
	}
}
