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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.Guia;
import com.exact.service.externa.service.interfaces.IDocumentoGuiaService;
import com.exact.service.externa.service.interfaces.IGuiaService;
import com.exact.service.externa.utils.CommonUtils;


@RestController
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	IGuiaService guiaService;
	
	@Autowired
	IDocumentoGuiaService documentoGuiaService;
	
	@GetMapping("/creados")
	public ResponseEntity<String> listarGuiasCreados() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Guia> guiasCreadas = guiaService.listarGuiasCreadas();
		
		CommonUtils cu = new CommonUtils();	    
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasCreadas,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> crearGuia(@RequestBody Guia guia, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();		
		
		Guia nuevaGuia = guiaService.crearGuia(guia, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
		if (nuevaGuia == null) {
			Map<String, Object> respuesta = new HashMap<String, Object>();
			respuesta.put("mensaje", "No existen documentos custodiados para la Guia");	
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
		
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		String dtoMapAsString = cu.filterObjetoJson(nuevaGuia, filter);
		
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
				
	}
	
	@PutMapping("/{guiaId}/documentos/{documentoId}/validacion")
	public ResponseEntity<?> validarDocumentoGuia(@PathVariable Long guiaId, @PathVariable Long documentoId, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		DocumentoGuia dg = documentoGuiaService.validarDocumentoGuia(guiaId, documentoId, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
		if (dg == null) {
			Map<String, Object> respuesta = new HashMap<String, Object>();
			respuesta.put("mensaje", "No existe el documento asociado a la guia.");	
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
		
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		String dtoMapAsString = cu.filterObjetoJson(dg, filter);
			
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	
	@PutMapping("{guiaId}/retiro")
	public ResponseEntity<?> eliminarDocumentosGuiaNoValidados(@PathVariable Long guiaId, Authentication authentication)  throws ClientProtocolException, IOException, JSONException{
		
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		Map<String, Object> respuesta = new HashMap<String, Object>();
		
		valor = guiaService.quitarDocumentosGuia(guiaId);
		
		switch(valor) {
		case 0: 
			rpta="NO EXISTE GUIA";
				status=HttpStatus.BAD_REQUEST;
				break;
		case 1: 
			rpta="GUIA ELIMINADA";
				status=HttpStatus.OK;
				break;
		case 2:	
			rpta ="ELEMENTOS RETIRADOS SATISFACTORIAMENTE";
				status=HttpStatus.OK;
				break;
		case 3:
			rpta="NO EXISTE ELEMENTOS NO VALIDADOS";
				status=HttpStatus.BAD_REQUEST;
				break;
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
	}
	
	
	@PutMapping("{guiaId}/envio")
	public ResponseEntity<?> enviarGuia(@PathVariable Long guiaId, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		valor = guiaService.enviarGuia(guiaId, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
		switch(valor) {
		case 0: 
				rpta="NO EXISTE GUIA";
				status=HttpStatus.BAD_REQUEST;
				break;
		case 1: 
				rpta="GUIA ENVIADA SATISFACTORIAMENTE ";
				status=HttpStatus.OK;
				break;
		case 2:	
				rpta ="EXISTEN DOCUMENTOS NO VALIDADOS";
				status=HttpStatus.BAD_REQUEST;
				break;
		case 3:	
			rpta ="NO SE PUEDE REALIZAR LA ACCIÓN, LA GUIA YA HA SIDO ENVIADA";
			status=HttpStatus.BAD_REQUEST;
			break;
		
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
		
		//		
	}
	
	@PutMapping("{guiaId}")
	public ResponseEntity<?> modificarGuia(@PathVariable Long guiaId, @RequestBody Guia guia) throws ClientProtocolException, IOException, JSONException{
		
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		guia.setId(guiaId);
		
		valor = guiaService.modificarGuia(guia);
		
		switch(valor) {
		case 0: 
				rpta="NO EXISTE GUIA";
				status=HttpStatus.BAD_REQUEST;
				break;
		case 1: 
				rpta="GUIA ACTUALIZADA SATISFACTORIAMENTE";
				status=HttpStatus.OK;
				break;
		case 2:	
				rpta ="ESTADO DE GUIA NO VALIDO";
				status=HttpStatus.BAD_REQUEST;
				break;
		
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
	}
	
	@DeleteMapping("{guiaId}")
	public ResponseEntity<?> eliminarGuia(@PathVariable Long guiaId) throws ClientProtocolException, IOException, JSONException{
		
		Map<String, Object> respuesta = new HashMap<String, Object>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		valor = guiaService.eliminarGuia(guiaId);
		
		switch(valor) {
		case 0: 
				rpta="NO EXISTE GUIA";
				status=HttpStatus.BAD_REQUEST;
				break;
		case 1: 
				rpta="GUIA ELIMINADA SATISFACTORIAMENTE";
				status=HttpStatus.OK;
				break;
		case 2:	
				rpta ="ESTADO DE GUIA NO VALIDO";
				status=HttpStatus.BAD_REQUEST;
				break;
		
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
		
	}
	
	@GetMapping("/paraproveedor")
	public ResponseEntity<String> listarGuiasParaProveedor() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Guia> guiasParaProveedor = guiaService.listarGuiasParaProveedor();
		
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasParaProveedor,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/sincerrar")
	public ResponseEntity<String> listarGuiasSinCerrar() throws ClientProtocolException, IOException, JSONException {
		
		Iterable<Guia> guiasSinCerrar = guiaService.listarGuiasSinCerrar();
		
		CommonUtils cu = new CommonUtils();	  
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasSinCerrar,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
}
