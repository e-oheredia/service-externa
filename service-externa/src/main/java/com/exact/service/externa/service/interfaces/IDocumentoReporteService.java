package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;

public interface IDocumentoReporteService {
	
	void insertarDocumentosReporte(Guia guia) throws IOException, JSONException;
	void actualizarDocumentosPorResultado(List<Documento> lstdocumento, List<Long> guiaIds) throws ClientProtocolException, IOException, JSONException, URISyntaxException, ParseException ;
	void actualizarDocumentosRecepcionados(Long documentoId);

}
