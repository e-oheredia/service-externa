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

import com.exact.service.externa.service.interfaces.IReporteService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	IReporteService reporteservice;
	
	
	@GetMapping("/volumen/curier")
	public ResponseEntity< Map<String, Float>> findSedeByMatricula(@PathVariable String matricula)
			throws IOException, JSONException {
		Map<String, Float> reportecurier = reporteservice.volumenbycurier();
		return new ResponseEntity<Map<String, Float> >(reportecurier,HttpStatus.OK);
	}
	
}
