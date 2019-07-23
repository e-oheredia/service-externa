package com.exact.service.externa.service.interfaces;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.Envio;
import com.exact.service.externa.entity.PlazoDistribucion;

public interface IEnvioService {
	Envio registrarEnvio(Envio envio, Long idUsuario, MultipartFile multipartFile, String header) throws IOException,ParseException, MessagingException, JSONException ;
	Iterable<Envio> listarEnviosNoAutorizados() throws IOException, Exception; 
	Envio autorizarEnvio(Long idEnvio, Long idUsuario, String header, String nombreUsuario) throws ParseException, IOException, JSONException;
	Envio denegarEnvio(Long idEnvio, Long idUsuario, String header) throws ParseException, IOException, JSONException;
	Iterable<Envio> listarEnviosCreados(String matricula) throws IOException, Exception;
	Iterable<Envio> listarEnviosAutorizacion(String fechaIni, String fechaFin) throws IOException, Exception; 
	Envio modificaPlazo(Long idEnvio, PlazoDistribucion plazo, Long idUsuario, String header) throws ParseException, IOException, JSONException;
	Iterable<Envio> listarEnviosInconsistencias(String fechaIni, String fechaFin) throws IOException, Exception; 
}
