package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import org.apache.http.ParseException;
import org.json.JSONException;




public interface IGestionUsuariosEdao {
	
	public String obtenerCorreoAutorizador(Long idVerificador, String header) throws IOException,JSONException ;
	public String findPerfil(Long usuarioId, String header) throws IOException,JSONException ;
	public String obtenerNombreUsuario(Long usuarioId, String header) throws IOException,JSONException ;
	public String obtenerCorreoUTD(String header) throws IOException,JSONException ;
	
}
