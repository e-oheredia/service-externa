package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IMenuEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;

@Repository
public class MenuEdao implements IMenuEdao{
	
	@Value("${service.menu}")
	private String menuPath;
	
	@Autowired
	private IRequester requester;
		
	private final String menusPath = "/menus";	

	@Override
	public Iterable<Map<String, Object>> listarMenuByPermisoIds(List<Long> permisoIds) throws ClientProtocolException, IOException, JSONException {
		HttpGet httpGet = new HttpGet(menuPath + menusPath + "?permisoIds=" + String.join(",", permisoIds.stream().map(id -> id.toString())
				.collect(Collectors.toList())));
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		try {
			if(httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String response = EntityUtils.toString(httpResponse.getEntity());
				JSONArray responseJson = new JSONArray(response);		
				return CommonUtils.jsonArrayToMap(responseJson);
			}else {
				return null;
			}
		} finally {
			httpResponse.close();
		}
		
	}
	
}
