package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.interfaces.IGuiaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	IGuiaService guiaService;
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarGuiasCreados() throws ClientProtocolException, IOException, JSONException {
		
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(guiaService.listarGuiasCreadas());
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> crearGuia(@RequestBody Guia guia, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();		
		
		Guia nuevaGuia = guiaService.crearGuia(guia, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
		if (nuevaGuia == null) {
			Map<String, Object> respuesta = new HashMap<String, Object>();
			respuesta.put("mensaje", "No existen documentos custodiados para la Guia.");	
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Guia>(nuevaGuia, HttpStatus.OK);
				
	}
}
