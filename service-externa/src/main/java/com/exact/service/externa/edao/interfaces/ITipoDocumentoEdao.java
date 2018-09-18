package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

public interface ITipoDocumentoEdao {
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException;
	Iterable<Map<String, Object>> listarByIds(Iterable<Long> ids) throws ClientProtocolException, IOException, JSONException;
}
