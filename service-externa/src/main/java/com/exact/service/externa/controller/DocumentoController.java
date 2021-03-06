package com.exact.service.externa.controller;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.exact.service.externa.entity.DocumentoGuia;
import com.exact.service.externa.entity.SeguimientoDocumento;
import com.exact.service.externa.entity.TipoDevolucion;
import com.exact.service.externa.service.classes.DocumentoGuiaService;
import com.exact.service.externa.service.classes.DocumentoService;
import com.exact.service.externa.utils.CommonUtils;

@EnableGlobalMethodSecurity(securedEnabled=true)
@RestController
@RequestMapping("/documentos")
public class DocumentoController {
	
	
	
	private static final String IDUSUARIO = "idUsuario";
	private static final String FECHAIMCOMPLETA = "VALOR DE FECHAS INCOMPLETAS";
	private static final String FECHANOVALIDA = "FORMATO DE FECHAS NO VALIDA";
	private static final String MATRICULA = "matricula";
	
	@Autowired
	private DocumentoService documentoService;
	
	@Autowired
	private DocumentoGuiaService documentoGuiaService;
	
	@PutMapping("/custodia")
	public ResponseEntity<Integer> custodiarDocumentos(@RequestBody List<Documento> documentos, Authentication authentication) {
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		return new ResponseEntity<>(
				documentoService.custodiarDocumentos(documentos, Long.valueOf(datosUsuario.get(IDUSUARIO).toString())), 
				HttpStatus.OK);
	}
	

	@GetMapping("/custodiados")
	public ResponseEntity<String> listarDocumentosCustodiados(Authentication authentication) throws IOException, JSONException{

		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		

		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////

		String dtoMapAsString = cu.filterListaObjetoJson(documentoService.listarDocumentosPorEstado(datosUsuario),filter);

		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	
	}
	@PutMapping("/cargaresultado")
	public ResponseEntity<?> cargarResultados(@RequestBody List<Documento> documentos, Authentication authentication) throws IOException, JSONException, URISyntaxException, ParseException {
		
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		
		Map<String, Object> respuesta = new HashMap<>();
		int valor;
		String rpta="";
		HttpStatus status = HttpStatus.OK;
		
		Map<Integer,String> resultado = documentoService.cargarResultados(documentos, Long.valueOf(datosUsuario.get(IDUSUARIO).toString()));
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
		default:
			status=HttpStatus.BAD_REQUEST;
			break;
		}
		
		respuesta.put("mensaje", rpta);	
		return new ResponseEntity<>(respuesta,status);
}

	@GetMapping("/consultabcp")
	public ResponseEntity<String> listarReporteBCP(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @RequestParam(name="idbuzon") Long idbuzon ) throws Exception {
		if(fechaini=="" || fechafin=="") 
		{
			return new ResponseEntity<>(FECHAIMCOMPLETA, HttpStatus.BAD_REQUEST);
		}
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaini);
			dateF = dt.parse(fechafin); 
		} catch (Exception e) {
			return new ResponseEntity<>(FECHANOVALIDA, HttpStatus.BAD_REQUEST);
		}
		
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) 
		{
			Iterable<Documento> documentosUbcp = documentoService.listarReporteBCP(dateI, dateF, idbuzon);
			
			if(documentosUbcp==null) {
				return new ResponseEntity<>("NO SE ENCONTRARON DOCUMENTOS", HttpStatus.NOT_FOUND);
			}
			
			CommonUtils cu = new CommonUtils();	    
			Map<String, String> filter = new HashMap<>();
			filter.put("envioFilter", "documentos");
			filter.put("documentosGuiaFilter", "documento");
			filter.put("guiaFilter", "documentosGuia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			///////////////////////////////////////////////////////////
		    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,filter);
		    return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
		    
		}
		return new ResponseEntity<>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/entregados")
	public ResponseEntity<String> listarDocumentosEntregados(Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Iterable<Documento> documentos = documentoService.listarDocumentosEntregados(datosUsuario.get(MATRICULA).toString());
		List<Documento> documentosParaCargo = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosParaCargo.isEmpty()) {
			return new ResponseEntity<>("NO SE ENCUENTRA DOCUMENTOS ENTREGADOS", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter","documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentosParaCargo,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	
	}
	
	@PutMapping("{id}/recepcioncargo")
	public ResponseEntity<String> recepcionarEntregados(@PathVariable Long id, Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		CommonUtils cu = new CommonUtils();
		Documento documento = documentoService.recepcionarDocumentoEntregado(id, Long.valueOf(datosUsuario.get(IDUSUARIO).toString()));
		if(documento==null) {
			return new ResponseEntity<>("NO ES UN DOCUMENTO ENTREGADO O YA SE ENCUENTRA RECEPCIONADO", HttpStatus.BAD_REQUEST);
		}
		
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter","documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(documento,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);	
	}
	
	@GetMapping("/pordevolver")
	public ResponseEntity<String> listarDocumentosDevueltosParaCargos(Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Iterable<Documento> documentos = documentoService.listarDocumentosDevueltos(datosUsuario.get(MATRICULA).toString());
		List<Documento> documentosdevueltos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosdevueltos.isEmpty()) {
			return new ResponseEntity<>("NO SE ENCUENTRA DOCUMENTOS DEVUELTOS O REZAGADOS", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentosdevueltos,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("{id}/recepciondevueltos")
	public ResponseEntity<String> recepcionardevueltos(@PathVariable Long id, Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		CommonUtils cu = new CommonUtils();
		Documento documento = documentoService.recepcionarDocumentoDevuelto(id, Long.valueOf(datosUsuario.get(IDUSUARIO).toString()));
		if(documento==null) {
			return new ResponseEntity<>("NO ES UN DOCUMENTO REZAGADO O DEVUELTO ", HttpStatus.BAD_REQUEST);
		}
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(documento,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);		
	}
	@GetMapping("/consultautd")
	public ResponseEntity<String> listarReporteUTD(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin, @RequestParam(name="autogenerado", required=false) String autogenerado ) throws Exception 
	{
		if(autogenerado!=null) {
			Documento documento = documentoService.listarDocumentoUTD(autogenerado);
			if(documento==null) {
				return new ResponseEntity<>("NO EXISTE DOCUMENTO CON ESE AUTOGENERADO ", HttpStatus.BAD_REQUEST);
			}
			CommonUtils cu = new CommonUtils();
			Map<String, String> filter = new HashMap<>();
			filter.put("envioFilter", "documentos");
			filter.put("documentosGuiaFilter", "documento");
			filter.put("guiaFilter", "documentosGuia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			///////////////////////////////////////////////////////////
			String dtoMapAsString = cu.filterObjetoJson(documento,filter);
			return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
		}
		else
		{
			if(fechaini=="" || fechafin=="") 
			{
				return new ResponseEntity<>(FECHAIMCOMPLETA, HttpStatus.BAD_REQUEST);
			}
			
			SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			Date dateI= null;
			Date dateF= null;
			
			try {
				dateI = dt.parse(fechaini);
				dateF = dt.parse(fechafin); 
			} catch (Exception e) {
				return new ResponseEntity<>(FECHANOVALIDA, HttpStatus.BAD_REQUEST);
			}
			
			if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) 
			{
				Iterable<Documento> documentosUbcp = documentoService.listarReporteUTD(dateI, dateF);
				CommonUtils cu = new CommonUtils();
				Map<String, String> filter = new HashMap<>();
				filter.put("envioFilter", "documentos");
				filter.put("documentosGuiaFilter", "documento");
				filter.put("guiaFilter", "documentosGuia");
				filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
				///////////////////////////////////////////////////////////
			    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,filter);
			    return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
			}
			return new ResponseEntity<>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@GetMapping("/documentosvolumen")
	public ResponseEntity<String> listarDocumentosVolumen(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin, @RequestParam Long estado  ) throws IOException, JSONException, ParseException, URISyntaxException 
	{ 
		
		
		if(fechaini=="" || fechafin=="") 
		{
			return new ResponseEntity<>(FECHAIMCOMPLETA, HttpStatus.BAD_REQUEST  );
		}
		
		
		if(estado==null) {
			return new ResponseEntity<>("NO SE ENCUENTRA ESTADO", HttpStatus.BAD_REQUEST);
		}
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		try {
			dateI = dt.parse(fechaini);
			dateF = dt.parse(fechafin); 
		} catch (Exception e) {
			return new ResponseEntity<>(FECHANOVALIDA, HttpStatus.BAD_REQUEST);
		}
		
		if(dateF.compareTo(dateI)>0 || dateF.equals(dateI)) 
		{
		
			Iterable<Documento> documentosUbcp = documentoService.listarDocumentosParaVolumen(dateI, dateF,estado);			
			if(documentosUbcp==null) {
				return new ResponseEntity<>("no existen documentos", HttpStatus.OK);
			}
			CommonUtils cu = new CommonUtils();	
			Map<String, String> filter = new HashMap<>();
			filter.put("envioFilter", "documentos");
			filter.put("documentosGuiaFilter", "documento");
			filter.put("guiaFilter", "documentosGuia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			///////////////////////////////////////////////////////////
		    String dtoMapAsString = cu.filterListaObjetoJson(documentosUbcp,filter);
		    return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
		}
		
		return new ResponseEntity<>("RANGO DE FECHA NO VALIDA", HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/documentoscargos")
	public ResponseEntity<String> listarDocumentosEntregadosParaCargos(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin) throws IOException, JSONException{
		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaini);
			dateF = dt.parse(fechafin); 
		} catch (Exception e) {
			return new ResponseEntity<>(FECHANOVALIDA, HttpStatus.BAD_REQUEST);
		}
		
		Iterable<Documento> documentos = documentoService.listarCargos(dateI,dateF);
		List<Documento> documentosCargos = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosCargos.isEmpty()) {
			return new ResponseEntity<>("NO SE ENCUENTRA DOCUMENTOS ENTREGADOS PARA CARGOS", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter","documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentosCargos,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	
	}
	
	@PostMapping("/{id}/cambioestado")
	public ResponseEntity<String> cambiarEstadoDocumento(@PathVariable Long id,@RequestBody SeguimientoDocumento seguimientoDocumento, Authentication authentication ) throws IOException, JSONException 
	{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Documento documento = documentoService.cambiarEstadoDocumento(id, seguimientoDocumento, Long.valueOf(datosUsuario.get(IDUSUARIO).toString()));
		if(documento==null) {
			return new ResponseEntity<>("Cambio de estado no permitido ", HttpStatus.BAD_REQUEST);
		}
			CommonUtils cu = new CommonUtils();
			Map<String, String> filter = new HashMap<>();
			filter.put("envioFilter", "documentos");
			filter.put("documentosGuiaFilter", "documento");
			filter.put("guiaFilter", "documentosGuia");
			filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
			///////////////////////////////////////////////////////////
			String dtoMapAsString = cu.filterObjetoJson(documento,filter);
			return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PostMapping("/{id}/codigodevolucion")
	public ResponseEntity<String> guardarCodigoDevolucion(@PathVariable Long id, @RequestBody String codigoDevolucion, Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Documento documento = documentoService.guardarCodigoDevolucion(id, codigoDevolucion,Long.valueOf(datosUsuario.get(IDUSUARIO).toString()));
		if(documento==null) {
			return new ResponseEntity<>("No se pudo guardar el codigo devolución", HttpStatus.BAD_REQUEST);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(documento,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/documentoguia")
	public ResponseEntity<String> listarDocumentosPorGuia(@PathVariable Long id) throws IOException, JSONException{
		Iterable<Documento> documentos = documentoService.listarDocumentosByGuia(id);
		List<Documento> documentosGuia = StreamSupport.stream(documentos.spliterator(), false).collect(Collectors.toList());
		if(documentosGuia.isEmpty()) {
			return new ResponseEntity<>("NO SE ENCUENTRA DOCUMENTOS PARA ESTA GUIA", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentosGuia,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/documentosrecepcion")
	public ResponseEntity<String> listarDocumentosaRecepcionar(Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Iterable<Documento> documentos = documentoService.listarDocumentosRecepcion(datosUsuario.get(MATRICULA).toString());
		if(documentos==null) {
			return new ResponseEntity<>("NO EXISTEN DOCUMENTOS PARA RECEPCIONAR", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterListaObjetoJson(documentos,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/listarecepcion")
	public ResponseEntity<String> listarDocumentoRecepcionado(@PathVariable Long id, Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		Documento documento = documentoService.listarDocumentoaRecepcionar(id, datosUsuario.get(MATRICULA).toString());
		if(documento==null) {
			return new ResponseEntity<>("NO SE PUEDE RECEPCIONAR EL DOCUMENTO", HttpStatus.NOT_FOUND);
		}
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(documento,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
	@PutMapping("{id}/recepcion")
	public ResponseEntity<String> recepcionCargosDocumentos(@PathVariable Long id, @RequestBody List<TipoDevolucion> tiposDevolucion,Authentication authentication) throws IOException, JSONException{
		@SuppressWarnings("unchecked")
		Map<String, Object> datosUsuario = (Map<String, Object>) authentication.getPrincipal();
		CommonUtils cu = new CommonUtils();
		Documento documento = documentoService.recepcionDocumento(id, Long.valueOf(datosUsuario.get(IDUSUARIO).toString()),tiposDevolucion);
		if(documento==null) {
			return new ResponseEntity<>("NO SE PUDO RECEPCIONAR", HttpStatus.BAD_REQUEST);
		}
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentosGuiaFilter", "documento");
		filter.put("guiaFilter","documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		///////////////////////////////////////////////////////////
		String dtoMapAsString = cu.filterObjetoJson(documento,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);	
	}
	
	@PutMapping("{id}/desvalidar")
	public ResponseEntity<String> desvalidarDocumentoGuia(@PathVariable Long id) throws IOException, JSONException{
		DocumentoGuia dg = documentoGuiaService.desvalidarDocumento(id);
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("envioFilter", "documentos");
		filter.put("documentoFilter", "documentosGuia");
		filter.put("guiaFilter", "documentosGuia");
		filter.put("estadoDocumentoFilter", "estadosDocumentoPermitidos");
		String dtoMapAsString = cu.filterObjetoJson(dg, filter);
			
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
	
}
