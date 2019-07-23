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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.interfaces.IDocumentoService;
import com.exact.service.externa.service.interfaces.IEnvioMasivoService;
import com.exact.service.externa.service.interfaces.IGuiaService;
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
	
	@Autowired
	IGuiaService guiaService;
	
	@Autowired
	IDocumentoService documentoService;

	//@Secured("ROLE_CREADOR_DOCUMENTO")
	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<String> registrarEnvioMasivo(@RequestParam("envioMasivo") String envioMasivoJsonString, @RequestParam(required=false) MultipartFile file,Authentication authentication,HttpServletRequest req ) throws IOException, JSONException, NumberFormatException, ParseException, MessagingException{
		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		String header = req.getHeader("Authorization");
		EnvioMasivo envioMasivo = mapper.readValue(envioMasivoJsonString, EnvioMasivo.class);		
		EnvioMasivo envioMasivoRegistrado = envioMasivoService.registrarEnvioMasivo(envioMasivo,Long.valueOf(datosUsuario.get("idUsuario").toString()), file,datosUsuario.get("matricula").toString() ,header,datosUsuario.get("perfil").toString());
		if(envioMasivoRegistrado==null) {
			return new ResponseEntity<String>("NO SE PUDO REGISTRAR EL ENVIO", HttpStatus.BAD_REQUEST);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("EnvioFilter", "inconsistenciasDocumento");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioMasivoRegistrado, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);		
		
	}
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarEnviosCreados(Authentication authentication) throws ClientProtocolException, IOException, JSONException {
		CommonUtils cu = new CommonUtils();
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("EnvioFilter", "inconsistenciasDocumento");
		filter.put("EnvioFilter", "documentos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(envioMasivoService.listarEnviosMasivosCreados(datosUsuario.get("matricula").toString()), filter);
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PostMapping("/bloque")
	public ResponseEntity<String> registrarEnvioBloque(@RequestParam("envioBloque") String envioJsonString,  @RequestParam(value="codigoGuia") String codigoGuia , @RequestParam(value="proveedorId") Long proveedorId, Authentication authentication, HttpServletRequest req) throws IOException, JSONException, NumberFormatException, ParseException, MessagingException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		ObjectMapper mapper = new ObjectMapper();
		EnvioMasivo envioBloque = mapper.readValue(envioJsonString, EnvioMasivo.class);		
		String header = req.getHeader("Authorization");
		EnvioMasivo envioBloqueNuevo = envioMasivoService.registrarEnvioMasivo(envioBloque,Long.valueOf(datosUsuario.get("idUsuario").toString()), null, datosUsuario.get("matricula").toString(),header,datosUsuario.get("perfil").toString());
		if(envioBloqueNuevo==null) {
			return new ResponseEntity<String>("NO SE PUDO REGISTRAR EL ENVIO", HttpStatus.BAD_REQUEST);
		}
		guiaService.crearGuiaBloque(envioBloqueNuevo, Long.valueOf(datosUsuario.get("idUsuario").toString()), codigoGuia, proveedorId,  datosUsuario.get("matricula").toString());
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("documentosFilter", "envio");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");		
		filter.put("EnvioFilter", "inconsistenciasDocumento");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(envioBloqueNuevo, filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("{id}/documentos")
	public ResponseEntity<?> findDocumentosPorEnvio(@PathVariable Long id, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Iterable<Documento> documentosBD = documentoService.listarDocumentosPorEnvioId(id, datosUsuario.get("matricula").toString());
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		String dtoMapAsString = cu.filterListaObjetoJson(documentosBD,filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
}
