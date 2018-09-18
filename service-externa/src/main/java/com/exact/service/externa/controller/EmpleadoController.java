package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IEmpleadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("empleados")
public class EmpleadoController {
	
	@Autowired
	IEmpleadoService empleadoService;
	
	@GetMapping("/autenticado")
	public ResponseEntity<String> listarEmpleadoAutenticado(Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		String matricula = (String) datosUsuario.get("matricula");
		Map<String, Object> empleado = empleadoService.listarByMatricula(matricula);
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(empleado);	    
		return new ResponseEntity<String>(dtoMapAsString, dtoMapAsString == null ? HttpStatus.NOT_FOUND: HttpStatus.OK);		
	}
	
}
