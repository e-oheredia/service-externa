package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.service.interfaces.IEstadoDocumentoService;
import com.exact.service.externa.utils.CommonUtils;

@RestController
@RequestMapping("/estadosdocumento")
public class EstadoDocumentoController {
	
	@Autowired
	IEstadoDocumentoService estadoDocumentoService;
	
	@GetMapping
	public ResponseEntity<String> listarAll() throws IOException, JSONException {
		Iterable<EstadoDocumento> eD = estadoDocumentoService.listarAll();
		List<EstadoDocumento> estadoDocumentos = StreamSupport.stream(eD.spliterator(), false).collect(Collectors.toList());
		CommonUtils cu = new CommonUtils();
		Map<String, String> filter = new HashMap<>();
		filter.put("estadosDocumentoPermitidosFilter", "estadosDocumentoPermitidos");
		String dtoMapAsString = cu.filterListaObjetoJson(estadoDocumentos,filter);
		return new ResponseEntity<>(dtoMapAsString, HttpStatus.OK);
	}
}
