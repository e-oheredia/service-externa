package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.AmbitoDistrito;

public interface IAmbitoDistritoService {

	public Iterable<AmbitoDistrito> listarAmbitoDistritos() throws ClientProtocolException, IOException, JSONException;
	public Iterable<AmbitoDistrito> validarActualizarAmbitoDistrito(List<Map<String,Object>> distritos) throws ClientProtocolException, IOException, JSONException;
}
