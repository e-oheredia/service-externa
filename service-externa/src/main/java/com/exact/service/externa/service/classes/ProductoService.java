package com.exact.service.externa.service.classes;

import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IProductoEdao;
import com.exact.service.externa.service.interfaces.IProductoService;

import io.jsonwebtoken.io.IOException;

@Service
public class ProductoService implements IProductoService {

	@Autowired
	IProductoEdao productoEdao;
	
	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException, Exception {
		return productoEdao.listarAll();
	}

	@Override
	public Map<String, Object> guardar(String producto) throws IOException, JSONException, Exception {
		return productoEdao.guardar(producto);
	}

	@Override
	public Map<String, Object> modificar(Long id, String producto) throws IOException, JSONException, Exception {
		return productoEdao.modificar(id, producto);
	}

	@Override
	public Iterable<Map<String, Object>> listarActivos() throws IOException, JSONException, Exception {
		return productoEdao.listarActivos();
	}


}
