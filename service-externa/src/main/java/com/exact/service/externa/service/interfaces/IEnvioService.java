package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;

public interface IEnvioService {
	Envio registrarEnvio(Envio envio, Long idUsuario, MultipartFile multipartFile) throws IOException;
	Iterable<Envio> listarEnviosNoAutorizados() throws ClientProtocolException, IOException, JSONException; 
	Envio autorizarEnvio(Long idEnvio, Long idUsuario);
	Envio denegarEnvio(Long idEnvio, Long idUsuario);
	Iterable<Envio> listarEnviosCreados() throws ClientProtocolException, IOException, JSONException;
	
}
