package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.EstadoDocumento;

public interface IEstadoDocumentoService {
	Iterable<EstadoDocumento> listarPorTipoEstadoDocumentoId(Long tipoEstadoDocumentoId)throws ClientProtocolException, IOException, JSONException;
	Iterable<EstadoDocumento> listarAll();
}
