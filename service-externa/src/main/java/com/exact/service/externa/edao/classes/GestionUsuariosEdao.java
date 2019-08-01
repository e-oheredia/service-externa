package com.exact.service.externa.edao.classes;

import java.io.IOException;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IGestionUsuariosEdao;
import com.exact.service.externa.request.IRequester;

@Repository
public class GestionUsuariosEdao implements IGestionUsuariosEdao{

	@Value("${service.usuarios}")
	private String serviceUsuariosPath;
	
	@Autowired
	private IRequester requester;
	
	@Override
	public String obtenerCorreoAutorizador(Long id, String header) throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil" + "/" + id +"/correousuario");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}
	
	@Override
	public String findPerfil(Long usuarioId ,String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil" + "/"+ usuarioId +"/usuario");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);

		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				
				
				return EntityUtils.toString(httpResponse.getEntity());
				
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}
	
	@Override
	public String obtenerNombreUsuario(Long id, String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/usuario" + "/" + id +"/nombre");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
				
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
	}
	
	@Override
	public String obtenerCorreoUTD(String header) throws ParseException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(serviceUsuariosPath + "/perfil"+ "/correoutd");
		httpGet.addHeader("Authorization", header);
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(httpResponse.getEntity());
				
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}

		
	}

}
