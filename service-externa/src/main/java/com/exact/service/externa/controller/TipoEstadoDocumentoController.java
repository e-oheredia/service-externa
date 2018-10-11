package com.exact.service.externa.controller;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.EstadoDocumento;
import com.exact.service.externa.service.interfaces.IEstadoDocumentoService;

@RestController
@RequestMapping("/tipoestadodocumento")
public class TipoEstadoDocumentoController {

	@Autowired
	IEstadoDocumentoService estadoDocumentoService;
	
	@GetMapping("/{tipoEstadoDocumentoId}/estadodocumento")
	public ResponseEntity<Iterable<EstadoDocumento>> listarPorTipoEstadoDocumento(@PathVariable Long tipoEstadoDocumentoId)throws ClientProtocolException, IOException, JSONException {
		
		Iterable<EstadoDocumento> estadoDocumentoList = estadoDocumentoService.listarPorTipoEstadoDocumentoId(tipoEstadoDocumentoId);
	
		return new ResponseEntity<Iterable<EstadoDocumento>>(estadoDocumentoList, HttpStatus.OK);
	}
}
