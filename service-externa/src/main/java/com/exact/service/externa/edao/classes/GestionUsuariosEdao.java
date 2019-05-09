package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IGestionUsuariosEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class GestionUsuariosEdao implements IGestionUsuariosEdao{

	@Value("${service.usuarios}")
	private String serviceUsuariosPath;
	
	@Autowired
	private IRequester requester;
	
	@Override
	public String obtenerCorreoAutorizador(Long id, String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil" + "/" + id +"/correousuario");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		return response;
		
	}
	
	@Override
	public String findPerfil(Long usuarioId ,String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil" + "/"+ usuarioId +"/usuario");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		return response;
		
	}
	
	@Override
	public String obtenerNombreUsuario(Long id, String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/usuario" + "/" + id +"/nombre");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		return response;
		
	}
	
	@Override
	public String obtenerCorreoUTD(String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil"+ "/correoutd");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		return response;
		
	}

}
