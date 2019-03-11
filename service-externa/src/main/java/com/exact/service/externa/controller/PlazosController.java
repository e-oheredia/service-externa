package com.exact.service.externa.controller;


import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardar(@RequestBody String plazo) throws IOException, JSONException{
		return new ResponseEntity<Map<String, Object>>(plazosService.guardar(plazo), HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> modificar(@PathVariable Long id, @RequestBody String plazo) throws IOException, JSONException{
		return new ResponseEntity<Map<String, Object>>(plazosService.modificar(id, plazo), HttpStatus.OK);
	}

}
