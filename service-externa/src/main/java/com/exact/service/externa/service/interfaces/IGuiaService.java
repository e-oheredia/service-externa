package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Guia;

public interface IGuiaService {

	Iterable<Guia> listarGuiasCreadas() throws ClientProtocolException, IOException, JSONException;
	Guia crearGuia(Guia guia, Long usuarioId) throws ClientProtocolException, IOException, JSONException;
}
