package com.exact.service.externa.service.interfaces;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Documento;
import com.exact.service.externa.entity.Guia;

public interface IDocumentoService {

	int custodiarDocumentos(Iterable<Documento> documentos, Long usuarioId);
	Iterable<Documento> listarDocumentosGuiaPorCrear(Guia guia) throws ClientProtocolException, IOException, JSONException;
	Iterable<Documento> listarDocumentosPorEstado()throws ClientProtocolException, IOException, JSONException;
}
