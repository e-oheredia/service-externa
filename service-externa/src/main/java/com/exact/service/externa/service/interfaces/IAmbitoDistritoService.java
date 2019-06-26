package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.AmbitoDistrito;

public interface IAmbitoDistritoService {

	public Iterable<AmbitoDistrito> listarAmbitoDistritos() throws ClientProtocolException, IOException, JSONException;
	
}
