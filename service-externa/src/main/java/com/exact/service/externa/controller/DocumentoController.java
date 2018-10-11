package com.exact.service.externa.controller;


import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.classes.DocumentoService;
import com.exact.service.externa.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@EnableGlobalMethodSecurity(securedEnabled=true)
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	
	@Autowired
	private DocumentoService documentoService;
	
	@PutMapping("/custodia")
	public ResponseEntity<Integer> custodiarDocumentos(@RequestBody List<Documento> documentos, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		return new ResponseEntity<Integer>(
				documentoService.custodiarDocumentos(documentos, Long.valueOf(datosUsuario.get("idUsuario").toString())), 
				HttpStatus.OK);
	}
	
	@PostMapping("/porcrearguia")
	public ResponseEntity<String> listarDocumentosGuiaPorCrear(@RequestBody Guia guia) throws ClientProtocolException, IOException, JSONException {
		CommonUtils cu = new CommonUtils();	    
	    String dtoMapAsString = cu.filterListaObjetoJson(documentoService.listarDocumentosGuiaPorCrear(guia),"envioFilter","documentos");
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/custodiados")
	public ResponseEntity<String> listarDocumentosCustodiados() throws ClientProtocolException, IOException, JSONException{
		CommonUtils cu = new CommonUtils();
		String dtoMapAsString = cu.filterListaObjetoJson(documentoService.listarDocumentosPorEstado(),"envioFilter","documentos");
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	
	}
	
	@GetMapping("/consultabcp")
	public ResponseEntity<String> listarReporteBCP(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin ) throws ClientProtocolException, IOException, JSONException, ParseException {
		if(fechaini=="" || fechafin=="") 
		{
			return new ResponseEntity<String>("VALOR DE FECHAS INCOMPLETAS", HttpStatus.BAD_REQUEST);
		}
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaini);
			dateF = dt.parse(fechafin); 
		} catch (Exception e) {
			return new ResponseEntity<String>("FORMATO DE FECHAS NO VALIDA", HttpStatus.BAD_REQUEST);
		}
		
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) 
		{
			Iterable<Documento> documentosUbcp = documentoService.listarReporteBCP(dateI, dateF);
			CommonUtils cu = new CommonUtils();	    
		    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,"envioFilter","documentos");
		    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		    
		}
		return new ResponseEntity<String>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
	}
	
}
