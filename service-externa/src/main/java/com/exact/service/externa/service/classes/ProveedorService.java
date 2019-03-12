package com.exact.service.externa.service.classes;



import java.io.IOException;
import java.util.Map;



import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IProveedorEdao;
import com.exact.service.externa.service.interfaces.IProveedorService;

@Service
public class ProveedorService implements IProveedorService{


	@Autowired
	IProveedorEdao proveedorEdao;


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
