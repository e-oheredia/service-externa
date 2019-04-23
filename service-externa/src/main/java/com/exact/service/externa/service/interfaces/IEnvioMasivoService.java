package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.EnvioMasivo;

public interface IEnvioMasivoService {
	EnvioMasivo registrarEnvioMasivo(EnvioMasivo envioMasivo, Long idUsuario, MultipartFile multipartFile,String matricula ,String header)  throws IOException, ParseException, MessagingException,  IOException, JSONException;
	Iterable<EnvioMasivo> listarEnviosMasivosCreados(String matricula) throws ClientProtocolException, IOException, JSONException;
}
