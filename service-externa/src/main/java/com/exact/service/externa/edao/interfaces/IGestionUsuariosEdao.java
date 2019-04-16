package com.exact.service.externa.edao.interfaces;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.json.JSONException;




public interface IGestionUsuariosEdao {
	
	public String obtenerCorreoAutorizador(String header) throws ParseException, IOException,JSONException ;
	public String findPerfil(Long usuarioId, String header) throws ParseException, IOException,JSONException ;
	
	
}
