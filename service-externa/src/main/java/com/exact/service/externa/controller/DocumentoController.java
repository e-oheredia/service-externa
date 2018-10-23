package com.exact.service.externa.controller;


import java.io.IOException;
import java.util.HashMap;
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
	
	
	@PutMapping("/cargaresultado")
	public ResponseEntity<?> cargarResultados(@RequestBody List<Documento> documentos, Authentication authentication) throws ClientProtocolException, IOException, JSONException {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		Map<Integer,String> resultado = documentoService.cargarResultados(documentos, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		int[] resultadoArray = resultado.keySet().stream().mapToInt(Integer::intValue).toArray();

		valor = resultadoArray[0];
		rpta = resultado.get(valor);
		
		switch(valor) {
		case 0: 				
				status=HttpStatus.BAD_REQUEST;
				break;
		case 1: 				
				status=HttpStatus.OK;
				break;
		case 2:
				status=HttpStatus.BAD_REQUEST;
				break;
		case 3:
			status=HttpStatus.BAD_REQUEST;
			break;
		case 4:	
			status=HttpStatus.BAD_REQUEST;
			break;
		case 5:	
			status=HttpStatus.BAD_REQUEST;
			break;
		case 6:	
			status=HttpStatus.BAD_REQUEST;
			break;
		
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
}

	@GetMapping("/consultabcp")
	public ResponseEntity<String> listarReporteBCP(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @RequestParam(name="idbuzon") Long idbuzon ) throws ClientProtocolException, IOException, JSONException, ParseException {
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
			Iterable<Documento> documentosUbcp = documentoService.listarReporteBCP(dateI, dateF, idbuzon);
			
			if(documentosUbcp==null) {
				return new ResponseEntity<String>("NO SE ENCONTRARON DOCUMENTOS", HttpStatus.NOT_FOUND);
			}
			
			CommonUtils cu = new CommonUtils();	    
		    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,"envioFilter","documentos");
		    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		    
		}
		return new ResponseEntity<String>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/entregados")
	public ResponseEntity<String> listarDocumentosEntregadosParaCargos() throws ClientProtocolException, IOException, JSONException{
		Iterable<Documento> documentos = documentoService.listarDocumentosEntregados();
		List<Documento> documentosParaCargo = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosParaCargo.size()==0) {
			return new ResponseEntity<String>("NO SE ENCUENTRA DOCUMENTOS ENTREGADOS", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		String dtoMapAsString = cu.filterListaObjetoJson(documentosParaCargo,"envioFilter","documentos");
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	
	}
	
	@PutMapping("{id}/recepcioncargo")
	public ResponseEntity<String> recepcionarEntregados(@PathVariable Long id, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		CommonUtils cu = new CommonUtils();
		Documento documento = documentoService.recepcionarDocumentoEntregado(id, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		if(documento==null) {
			return new ResponseEntity<String>("NO ES UN DOCUMENTO ENTREGADO O YA SE ENCUENTRA RECEPCIONADO", HttpStatus.BAD_REQUEST);
		}
		String dtoMapAsString = cu.filterObjetoJson(documento,"envioFilter","documentos");
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);	
	}
	
	@GetMapping("/pordevolver")
	public ResponseEntity<String> listarDocumentosDevueltosParaCargos() throws ClientProtocolException, IOException, JSONException{
		Iterable<Documento> documentos = documentoService.listarDocumentosDevueltos();
		List<Documento> documentosdevueltos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosdevueltos.size()==0) {
			return new ResponseEntity<String>("NO SE ENCUENTRA DOCUMENTOS DEVUELTOS O REZAGADOS", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		String dtoMapAsString = cu.filterListaObjetoJson(documentosdevueltos,"envioFilter","documentos");
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("{id}/recepciondevueltos")
	public ResponseEntity<String> recepcionardevueltos(@PathVariable Long id, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		CommonUtils cu = new CommonUtils();
		Documento documento = documentoService.recepcionarDocumentoDevuelto(id, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		if(documento==null) {
			return new ResponseEntity<String>("NO ES UN DOCUMENTO REZAGADO O DEVUELTO ", HttpStatus.BAD_REQUEST);
		}
		String dtoMapAsString = cu.filterObjetoJson(documento,"envioFilter","documentos");
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);		
	}
	@GetMapping("/consultautd")
	public ResponseEntity<String> listarReporteUTD(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin, @RequestParam(name="autogenerado", required=false) String autogenerado ) throws ClientProtocolException, IOException, JSONException, ParseException 
	{
		if(autogenerado!=null) {
			Documento documento = documentoService.listarDocumentoUTD(autogenerado);
			if(documento==null) {
				return new ResponseEntity<String>("NO EXISTE DOCUMENTO CON ESE AUTOGENERADO ", HttpStatus.BAD_REQUEST);
			}
			CommonUtils cu = new CommonUtils();
			String dtoMapAsString = cu.filterObjetoJson(documento,"envioFilter","documentos");
			return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		}
		else
		{
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
				Iterable<Documento> documentosUbcp = documentoService.listarReporteUTD(dateI, dateF);
				CommonUtils cu = new CommonUtils();	    
			    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,"envioFilter","documentos");
			    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
			}
			return new ResponseEntity<String>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/documentosvolumen")
	public ResponseEntity<String> listarDocumentosVolumen(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin ) throws ClientProtocolException, IOException, JSONException, ParseException 
	{ 
		
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
			Iterable<Documento> documentosUbcp = documentoService.listarDocumentosParaVolumen(dateI, dateF);
			CommonUtils cu = new CommonUtils();	    
		    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,"envioFilter","documentos");
		    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		}
		return new ResponseEntity<String>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
		
		
	}
	
}
