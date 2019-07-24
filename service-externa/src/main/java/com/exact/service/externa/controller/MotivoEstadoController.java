package com.exact.service.externa.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.MotivoEstado;
import com.exact.service.externa.service.interfaces.IMotivoEstadoService;

@RestController
@RequestMapping("/motivosestados")
public class MotivoEstadoController {

	@Autowired
	IMotivoEstadoService motivoestado;
	
	@GetMapping()
	public ResponseEntity<Iterable<MotivoEstado>> listarMotivos() throws ClientProtocolException, IOException, JSONException{
		return new ResponseEntity<Iterable<MotivoEstado>>(motivoestado.listarMotivos() , HttpStatus.OK);
	}
	
	
	
	
}
