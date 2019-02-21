package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;

public interface IDocumentoService {

	int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId);
	Iterable<Documento> listarDocumentosGuiaPorCrear(Guia guia, String matricula) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosPorEstado()throws ClientProtocolException, IOException, JSONException;
	Map<Integer,String> cargarResultados(List<Documento> documentos, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarReporteBCP(Date fechaIni, Date fechaFin, Long idbuzon) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosEntregados(String matricula) throws ClientProtocolException, IOException, JSONException;
	Documento recepcionarDocumentoEntregado(Long id, Long idUsuario) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosDevueltos(String matricula) throws ClientProtocolException, IOException, JSONException;
	Documento recepcionarDocumentoDevuelto(Long id, Long idUsuario) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarReporteUTD(Date fechaIni, Date fechaFin) throws ClientProtocolException, IOException, JSONException;
	Documento listarDocumentoUTD(String autogenerado) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosParaVolumen(Date fechaIni, Date fechaFin, Long estadoDocumentoId) throws ClientProtocolException, IOException, JSONException;

}
