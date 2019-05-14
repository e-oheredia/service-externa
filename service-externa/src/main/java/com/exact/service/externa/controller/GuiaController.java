package com.exact.service.externa.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.Documento;
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
	public ResponseEntity<String> listarGuiasCreados(Authentication authentication) throws ClientProtocolException, IOException, JSONException,Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		Iterable<Guia> guiasCreadas = guiaService.listarGuiasCreadas(datosUsuario.get("matricula").toString());
		CommonUtils cu = new CommonUtils();	    
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");				
		
		String dtoMapAsString = cu.filterListaObjetoJson(guiasCreadas,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/creadosbloque")
	public ResponseEntity<String> listarGuiasBloques(Authentication authentication) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		Iterable<Guia> guiasCreadas = guiaService.listarGuiasBloques(Long.valueOf(datosUsuario.get("idUsuario").toString()),datosUsuario.get("matricula").toString());
		CommonUtils cu = new CommonUtils();	    
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");				
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasCreadas,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}	
	
	
	
	@PostMapping
	public ResponseEntity<?> crearGuia(@RequestBody Guia guia, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();	
		Guia nuevaGuia = guiaService.crearGuiaRegular(guia, Long.valueOf(datosUsuario.get("idUsuario").toString()), datosUsuario.get("matricula").toString());
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
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");				
		
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
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");			
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
		
		valor = guiaService.enviarGuiaRegular(guiaId, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		
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
	
	@GetMapping("/procesarguias")
	public ResponseEntity<String> listarGuiasRegularParaProveedor() throws ClientProtocolException, IOException, JSONException, Exception {
		
		Iterable<Guia> guiasParaProveedor = guiaService.listarGuiasParaProveedor();
		
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");	
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasParaProveedor,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/sincerrar")
	public ResponseEntity<String> listarGuiasSinCerrar() throws ClientProtocolException, IOException, JSONException, Exception {
		
		Iterable<Guia> guiasSinCerrar = guiaService.listarGuiasSinCerrar();
		
		CommonUtils cu = new CommonUtils();	  
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");			
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasSinCerrar,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("{id}/desvalidar")
	public ResponseEntity<?> desvalidarDocumentoGuia(@PathVariable Long id) throws ClientProtocolException, IOException, JSONException{
		DocumentoGuia dg = documentoGuiaService.desvalidarDocumento(id);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		String dtoMapAsString = cu.filterObjetoJson(dg, filter);
			
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/reporteguias")
	public ResponseEntity<String> listarGuiasParaReporte(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin, @RequestParam(name="numeroGuia", required=false) String numeroGuia ) throws ClientProtocolException, IOException, JSONException, ParseException, Exception 
	{
		if(numeroGuia!=null){
			Guia guia = guiaService.listarPorNumeroGuia(numeroGuia);
			if(guia==null) {
				return new ResponseEntity<String>("No existe Guia con ese numero", HttpStatus.BAD_REQUEST);
			}
			CommonUtils cu = new CommonUtils();
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("envioFilter", "documentos");
			filter.put("documentoFilter", "documentosGuia");
			filter.put("documentosGuiaFilter", "guia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			///////////////////////////////////////////////////////////
			String dtoMapAsString = cu.filterObjetoJson(guia,filter);
			return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		}
		else{
			if(fechaini=="" || fechafin==""){
				return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
			}
			Iterable<Guia> guias = guiaService.listarGuiasPorFechas(fechaini, fechafin);
			if(guias==null) {
				return new ResponseEntity<String>("Error en Listar Guías", HttpStatus.BAD_REQUEST);
			}
			CommonUtils cu = new CommonUtils();
			Map<String, String> filter = new HashMap<String, String>();
			filter.put("envioFilter", "documentos");
			filter.put("documentoFilter", "documentosGuia");
			filter.put("documentosGuiaFilter", "guia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			filter.put("GuiaFilter", "documentosGuia");	
			///////////////////////////////////////////////////////////
		    String dtoMapAsString = cu.filterListaObjetoJson(guias,filter);
		    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
		}
	}
	
	@PutMapping("{id}/descarga")
	public ResponseEntity<?> fechaDescargaGuia(@PathVariable Long id, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Guia guia = guiaService.fechaDescargaGuia(id, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("GuiaFilter", "documentosGuia");		
		String dtoMapAsString = cu.filterObjetoJson(guia, filter);
			
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	
	@PutMapping("{guiaId}/enviobloque")
	public ResponseEntity<String> enviarGuiaBloque(@PathVariable Long guiaId, Authentication authentication) throws ClientProtocolException, IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Guia guia = guiaService.enviarGuiaBloque(guiaId, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("GuiaFilter", "documentosGuia");	
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("documentosGuiaFilter", "guia");
		String dtoMapAsString = cu.filterObjetoJson(guia, filter);
			
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/guiasbloque")
	public ResponseEntity<String> listarGuiasBloqueCompletadas(Authentication authentication) throws ClientProtocolException, IOException, JSONException, Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Iterable<Guia> guiasParaProveedor = guiaService.listarGuiasBloqueCompletadas(Long.valueOf(datosUsuario.get("idUsuario").toString()), datosUsuario.get("matricula").toString());
		
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("documentosGuiaFilter", "guia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");	
	    String dtoMapAsString = cu.filterListaObjetoJson(guiasParaProveedor,filter);
		
	    return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("{id}/documentos")
	public ResponseEntity<String> listarDocumentosPorGuiaId(@PathVariable Long id) throws Exception{
		Iterable<Documento> documentosBD = guiaService.listarDocumentosPorGuiaId(id);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		filter.put("GuiaFilter", "documentosGuia");	
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentosBD,filter);
		return new ResponseEntity<String>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("/cargadevolucionbloque")
	public ResponseEntity<?> cargarResultadosDevolucion(@RequestBody List<Documento> documentos, Authentication authentication) throws NumberFormatException, Exception{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Map<String, Object> respuesta = new HashMap<String, Object>();
		Map<Integer,String> resultado = guiaService.cargarResultadosDevolucion(documentos, Long.valueOf(datosUsuario.get("idUsuario").toString()));
		int[] resultadoArray = resultado.keySet().stream().mapToInt(Integer::intValue).toArray();
		int valor = resultadoArray[0];
		String rpta = resultado.get(valor);
		HttpStatus status = HttpStatus.BAD_REQUEST;
		if(valor==1) {
			status=HttpStatus.OK;
		}
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<Map<String, Object>>(respuesta,status);
	}
	
}
