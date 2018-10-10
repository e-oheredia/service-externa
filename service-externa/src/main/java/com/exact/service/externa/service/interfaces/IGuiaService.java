package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Guia;

public interface IGuiaService {

	Iterable<Guia> listarGuiasCreadas() throws ClientProtocolException, IOException, JSONException;
	Guia crearGuia(Guia guia, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	int quitarDocumentosGuia(Long guiaId) throws ClientProtocolException, IOException, JSONException;
	int enviarGuia (Long guiaId, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
	int modificarGuia(Guia guia) throws  ClientProtocolException, IOException, JSONException;
	int eliminarGuia(Long guiaId) throws  ClientProtocolException, IOException, JSONException;
	Iterable<Guia> listarGuiasParaProveedor()throws  ClientProtocolException, IOException, JSONException;
}
