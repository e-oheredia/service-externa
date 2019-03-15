																																																											package com.exact.service.externa.service.classes;



import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IProveedorService;

@Service
public class ProveedorService implements IProveedorService{


	@Autowired
	IProveedorDao proveedorDao;
	
	@Override
	public Iterable<Proveedor> listarProveedores() throws ClientProtocolException, IOException, JSONException {
		Iterable<Proveedor> proveedoresCreados = proveedorDao.findAll();
		List<Proveedor> proveedoresCreadosList = StreamSupport.stream(proveedoresCreados.spliterator(), false).collect(Collectors.toList());
		
		return proveedoresCreadosList;
	}

	@Override
	public Proveedor guardar(Proveedor proveedor) {
		return proveedorDao.save(proveedor);
	}

	@Override
	public Proveedor modificar(Proveedor proveedor) {
		if(proveedorDao.existsById(proveedor.getId())) {
			return guardar(proveedor);
		}else
			return null;
	}



}
