package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import com.exact.service.externa.entity.Proveedor;



public interface IProveedorService {


	Iterable<Proveedor> listarProveedores() throws ClientProtocolException, IOException, JSONException;
	Proveedor guardar(Proveedor proveedor);
	Proveedor modificar(Proveedor proveedor);

	
}
