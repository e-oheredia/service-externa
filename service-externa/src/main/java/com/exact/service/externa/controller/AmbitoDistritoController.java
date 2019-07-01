package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.AmbitoDistrito;
import com.exact.service.externa.service.interfaces.IAmbitoDistritoService;

@RestController
@RequestMapping("/ambitodistrito")
public class AmbitoDistritoController {
	
	@Autowired
	IAmbitoDistritoService ambitoDistritoService;

	@GetMapping()
	public ResponseEntity<Iterable<AmbitoDistrito>> listarAll()
			throws IOException, JSONException {
		return new ResponseEntity<Iterable<AmbitoDistrito>>(ambitoDistritoService.listarAmbitoDistritos(), HttpStatus.OK);
	}
	@PutMapping()
	public ResponseEntity<Map<String, Object>> subirUbigeos(@RequestBody List<Map<String, Object>> distritos)throws IOException, JSONException {
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Iterable<AmbitoDistrito> ambitosactualizados = ambitoDistritoService.validarActualizarAmbitoDistrito(distritos);
		if(ambitosactualizados==null) {
			respuesta.put("mensaje", "No se actualizaron los ubigeos");
		}
			respuesta.put("mensaje", "Se actualizaron los ubigeos");
		return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
	}
	
	
	
}
