package com.exact.service.externa.edao.classes;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.exact.service.externa.edao.interfaces.IPeriodoEdao;
import com.exact.service.externa.request.IRequester;
import com.exact.service.externa.utils.CommonUtils;
@Repository
public class PeriodoEdao  implements IPeriodoEdao{

	@Value("${service.dias-laborales}")
	private String ambitosPath;
	
	private final String path = "/periodos";
	
	@Autowired
	private IRequester requester;
	
	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException {
		HttpGet httpGet = new HttpGet(ambitosPath + path );
		CloseableHttpResponse httpResponse = requester.request(httpGet);
		String response = EntityUtils.toString(httpResponse.getEntity());
		JSONArray responseJson = new JSONArray(response);		
		return CommonUtils.jsonArrayToMap(responseJson);
	}

}
