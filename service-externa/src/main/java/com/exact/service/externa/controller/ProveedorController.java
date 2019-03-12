package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.dao.IProveedorPlazoDistribucionDao;
import com.exact.service.externa.entity.ProveedorPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IProveedorPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

	@Autowired
	IProveedorService proveedorService;
	
	@Autowired
	IProveedorPlazoDistribucionService proveedorPlazoDistribucionService;
	
	@GetMapping("{id}/plazosdistribucion")
	public ResponseEntity<Iterable<Map<String, Object>>> listarPlazosDistribucionPorProveedor(@PathVariable Long id) throws ClientProtocolException, IOException, JSONException {
		Iterable<Map<String, Object>> proveedorPlazoDistribucion = proveedorPlazoDistribucionService.getPlazoPorProveedorId(id);
		return new ResponseEntity<Iterable<Map<String, Object>>>(proveedorPlazoDistribucion,
				proveedorPlazoDistribucion == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<Iterable<Map<String, Object>>> listarProveedores() throws IOException, JSONException{
		return new ResponseEntity<Iterable<Map<String, Object>>>(proveedorService.listarAll(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<String> guardarProveedorPlazos(@RequestBody String proveedor) throws IOException, JSONException{
		JSONObject requestJson = new JSONObject(proveedor);
		JSONArray lstplazos = requestJson.getJSONArray("plazos");
		requestJson.remove("plazos");
		Map<String, Object> proveedorBD = proveedorService.guardarProveedor(requestJson.toString());
		proveedorPlazoDistribucionService.guardarProveedorPlazos(Long.valueOf(proveedorBD.get("id").toString()), lstplazos);
		return new ResponseEntity<String>(proveedorBD.toString(), HttpStatus.OK);
		
	}
	
	@PutMapping("/{idProveedor}")
	public ResponseEntity<Map<String, Object>> modificarProveedorPlazos(@PathVariable Long idProveedor, @RequestBody String proveedor) throws IOException, JSONException{
		JSONObject requestJson = new JSONObject(proveedor);
		JSONArray lstplazos = requestJson.getJSONArray("plazos");
		requestJson.remove("plazos");
		proveedorService.modificar(idProveedor, requestJson.toString());
		proveedorPlazoDistribucionService.actualizarProveedorPlazos(idProveedor, lstplazos);
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}
	
}
