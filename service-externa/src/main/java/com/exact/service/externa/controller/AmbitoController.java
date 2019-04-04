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


import com.exact.service.externa.service.interfaces.IAmbitoService;

@RestController
@RequestMapping("/ambitos")
public class AmbitoController {
	
	@Autowired
	IAmbitoService ambitoService;
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarAllAmbitos() throws IOException, JSONException, Exception {
		return new ResponseEntity<Iterable<Map<String, Object>>>(ambitoService.listarAmbitos(), HttpStatus.OK);
	}
	
	@GetMapping("/subambitos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarAllSubAmbitos() throws IOException, JSONException, Exception {
		return new ResponseEntity<Iterable<Map<String, Object>>>(ambitoService.listarSubAmbitos(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/subambitos/activos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarSubAmbitosActivosByAmbitoId(@PathVariable Long id) throws IOException, JSONException, Exception{
		return new ResponseEntity<Iterable<Map<String, Object>>>(ambitoService.listarSubAmbitosByAmbitoId(id) , HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardarSubAmbito(@RequestBody String subambito) throws IOException, JSONException, Exception{
		return new ResponseEntity<Map<String, Object>>(ambitoService.guardarSubAmbito(subambito) , HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> modificarSubAmbito(@PathVariable Long id, @RequestBody String subambito) throws IOException, JSONException, Exception{
		return new ResponseEntity<Map<String, Object>>(ambitoService.modificarSubAmbito(id, subambito) , HttpStatus.OK);
	}
}
