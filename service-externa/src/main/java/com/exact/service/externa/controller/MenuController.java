package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.IMenuService;
import com.exact.service.externa.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/menus")
public class MenuController {

	@Autowired
	IMenuService menuService;
	
	@GetMapping
	public ResponseEntity<String> listarMenuAutenticado(Authentication authentication) throws JsonProcessingException, ClientProtocolException, IOException, JSONException {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
			
		List<Long> permisoIds = ((List<Map<String, Object>>) CommonUtils.jsonArrayToMap(new JSONArray(datosUsuario.get("permisos").toString()))).stream()
				.map(permiso -> Long.parseLong(permiso.get("id").toString())).collect(Collectors.toList());
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    String dtoMapAsString = mapper.writeValueAsString(menuService.listarMenuByPermisoIds(permisoIds));
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}

}
