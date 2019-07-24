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


import com.exact.service.externa.service.interfaces.ISedeService;

@RestController
@RequestMapping("/sedes")
public class SedeController {

	@Autowired
	ISedeService sedeService;
	
	@GetMapping("/despacho/{matricula}")
	public ResponseEntity<Map<String, Object>> findSedeByMatricula(@PathVariable String matricula)
			throws IOException, JSONException {
		Map<String, Object> sede = sedeService.findSedeByMatricula(matricula);
		return new ResponseEntity<>(sede, sede == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@GetMapping("/sedesdespacho")
	public ResponseEntity<Iterable<Map<String, Object>>> listarSedesDespacho() throws IOException, JSONException{
		return new ResponseEntity<>(sedeService.listarSedesDespacho(), HttpStatus.OK);
	}
	
}
