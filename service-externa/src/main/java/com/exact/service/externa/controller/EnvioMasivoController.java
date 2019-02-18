package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.service.interfaces.IEnvioMasivoService;
import com.exact.service.externa.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@RequestMapping("/enviosmasivos")
public class EnvioMasivoController {
	
	@Autowired
	IEnvioMasivoService envioMasivoService;

	//@Secured("ROLE_CREADOR_DOCUMENTO")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<String> registrarEnvioMasivo(@RequestParam("envioMasivo") String envioMasivoJsonString, @RequestParam(required=false) MultipartFile file,Authentication authentication) throws IOException, JSONException{
		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		EnvioMasivo envioMasivo = mapper.readValue(envioMasivoJsonString, EnvioMasivo.class);		
		EnvioMasivo envioMasivoRegistrado = envioMasivoService.registrarEnvioMasivo(envioMasivo,Long.valueOf(datosUsuario.get("idUsuario").toString()), file);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioMasivoRegistrado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);		
		
	}
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarEnviosCreados() throws ClientProtocolException, IOException, JSONException {
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "documento");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(envioMasivoService.listarEnviosMasivosCreados(), filter);
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
}
