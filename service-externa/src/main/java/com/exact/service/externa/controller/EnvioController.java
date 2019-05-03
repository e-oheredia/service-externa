package com.exact.service.externa.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IEnvioService;
import com.exact.service.externa.service.interfaces.IGuiaService;
import com.exact.service.externa.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import static com.exact.service.externa.enumerator.EstadoTipoEnvio.ENVIO_BLOQUE;

@RestController
@RequestMapping("/envios")
public class EnvioController {

	@Autowired
	IEnvioService envioService;
	
	@Autowired
	IGuiaService guiaService;

	//@Secured("ROLE_CREADOR_DOCUMENTO")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<String> registrarEnvio(@RequestParam("envio") String envioJsonString, @RequestParam(required=false) MultipartFile file,  @RequestParam(value="codigoGuia",required=false) String codigoGuia , @RequestParam(value="proveedorId",required=false) Long proveedorId, Authentication authentication, HttpServletRequest req) throws IOException, JSONException, NumberFormatException, ParseException, MessagingException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		Envio envio = mapper.readValue(envioJsonString, Envio.class);		
		String header = req.getHeader("Authorization");
		Envio envioRegistrado = envioService.registrarEnvio(envio,Long.valueOf(datosUsuario.get("idUsuario").toString()), file, header);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioRegistrado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		
	}
	
	@PutMapping("/{id}/autorizacion")
	public ResponseEntity<String> autorizarEnvio(@PathVariable Long id, Authentication authentication, HttpServletRequest req) throws ClientProtocolException, IOException, JSONException {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		String header = req.getHeader("Authorization");
		Envio envioAutorizado = envioService.autorizarEnvio(id,Long.valueOf(datosUsuario.get("idUsuario").toString()),header);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioAutorizado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/denegacion")
	public ResponseEntity<String> denegarEnvio(@PathVariable Long id, Authentication authentication, HttpServletRequest req) throws ClientProtocolException, IOException, JSONException {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		String header = req.getHeader("Authorization");
		Envio envioDenegado = envioService.denegarEnvio(id,Long.valueOf(datosUsuario.get("idUsuario").toString()),header);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioDenegado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/noautorizados")
	public ResponseEntity<String> listarEnviosNoAutorizados() throws Exception {
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(envioService.listarEnviosNoAutorizados(), filter);
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarEnviosCreados(Authentication authentication) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString =  cu.filterListaObjetoJson(envioService.listarEnviosCreados(datosUsuario.get("matricula").toString()), filter);
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/enviosautorizacion")
	public ResponseEntity<String> listarEnviosAutorizacion(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws Exception {
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(envioService.listarEnviosAutorizacion(fechaini,fechafin), filter);
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/modificautorizacion")
	public ResponseEntity<?> modificarPlazoAutorizacion(@PathVariable Long id, @RequestBody PlazoDistribucion plazo, Authentication authentication, HttpServletRequest req) throws Exception, IOException {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		String header = req.getHeader("Authorization");
		Envio envioModificado = envioService.modificaPlazo(id, plazo,Long.valueOf(datosUsuario.get("idUsuario").toString()),header);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioModificado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	

	

}