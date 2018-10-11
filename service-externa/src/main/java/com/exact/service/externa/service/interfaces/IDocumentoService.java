package com.exact.service.externa.service.interfaces;
import java.io.IOException;
import java.util.List;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;

public interface IDocumentoService {

	int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId);
	Iterable<Documento> listarDocumentosGuiaPorCrear(Guia guia) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosPorEstado()throws ClientProtocolException, IOException, JSONException;
	int cargarResultados(List<Documento> documentos) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin) throws ClientProtocolException, IOException, JSONException;
}
