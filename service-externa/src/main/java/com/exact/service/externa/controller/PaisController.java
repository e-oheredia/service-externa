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
import com.exact.service.externa.service.interfaces.IDepartamentoService;

@RestController
@RequestMapping("paises")
public class PaisController {
	
	@Autowired
	IDepartamentoService departamentoService;
	
	@GetMapping("/{id}/departamentos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarProvinciaByDepartamentoId(@PathVariable("id") Long id) throws ClientProtocolException, IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String,Object>>>(departamentoService.listarDepartamentosByPaisId(id), HttpStatus.OK);
	}
	
}
