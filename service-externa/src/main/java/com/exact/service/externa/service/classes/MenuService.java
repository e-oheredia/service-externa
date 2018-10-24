package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IMenuEdao;
import com.exact.service.externa.service.interfaces.IMenuService;

@Service
public class MenuService implements IMenuService {
	
	@Autowired
	IMenuEdao menuEdao;

	@Override
	public Iterable<Map<String, Object>> listarMenuByPermisoIds(List<Long> permisoIds) throws ClientProtocolException, IOException, JSONException {
		return menuEdao.listarMenuByPermisoIds(permisoIds);
	}

}
