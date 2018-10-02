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


@RestController
@RequestMapping("/guias")
public class GuiaController {

	@Autowired
	IGuiaService guiaService;
	
	@Autowired
	IDocumentoGuiaService documentoGuiaService;
	
	@GetMapping("/creados")
	public ResponseEntity<Iterable<Guia>> listarGuiasCreados() throws ClientProtocolException, IOException, JSONException {
		
		
	    return new ResponseEntity<Iterable<Guia>>(guiaService.listarGuiasCreadas(), HttpStatus.OK);
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
		
		return new ResponseEntity<Guia>(nuevaGuia, HttpStatus.OK);
				
	}
	
	@PutMapping("/{guiaId}/documentos/{documentoId}/validacion")
	public ResponseEntity<?> validarDocumentoGuia(@PathVariable Long guiaId, @PathVariable Long documentoId, Authentication authentication){
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		DocumentoGuia dg = documentoGuiaService.validarDocumentoGuia(guiaId, documentoId, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
		if (dg == null) {
			Map<String, Object> respuesta = new HashMap<String, Object>();
			respuesta.put("mensaje", "No existe el documento asociado a la guia.");	
			return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.BAD_REQUEST);
		}
			
		return new ResponseEntity<DocumentoGuia>(dg, HttpStatus.OK);
	}
	
	
	@PutMapping("{guiaId}/eliminanovalidados")
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
	
	
	@PostMapping("/enviarguia")
	public ResponseEntity<?> enviarGuia(@RequestBody Guia guia, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		valor = guiaService.enviarGuia(guia, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
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
		
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
		
		//		
	}
	
}
