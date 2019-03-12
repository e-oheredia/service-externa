package com.exact.service.externa.service.classes;



import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.exact.service.externa.edao.interfaces.IProveedorEdao;
import com.exact.service.externa.entity.Guia;

import com.exact.service.externa.service.interfaces.IProveedorService;

@Service
public class ProveedorService implements IProveedorService{

//	@Autowired
//	IProveedorDao proveedorDao;
	
	@Autowired
	IProveedorEdao proveedorEdao;
//	
//	@Override
//	public Iterable<Proveedor> listarProveedores() throws ClientProtocolException, IOException, JSONException {
//		Iterable<Proveedor> proveedoresCreados = proveedorDao.findAll();
//		List<Proveedor> proveedoresCreadosList = StreamSupport.stream(proveedoresCreados.spliterator(), false).collect(Collectors.toList());
//		
//		return proveedoresCreadosList;
//	}

	@Override
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException {
		return proveedorEdao.listarAll();
	}

	@Override
	public Map<String, Object> guardarProveedor(String proveedor) throws IOException, JSONException {
		return proveedorEdao.guardar(proveedor);
	}

	@Override
	public Map<String, Object> modificar(Long id, String proveedor) throws IOException, JSONException {
		return proveedorEdao.modificar(id, proveedor);
	}

}
