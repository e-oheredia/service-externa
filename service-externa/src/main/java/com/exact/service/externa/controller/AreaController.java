package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IAreaService;

@RestController
@RequestMapping("/areas")
public class AreaController {

	@Autowired
	IAreaPlazoDistribucionService areaPlazoDistribucionService;
	@Autowired
	IAreaService areaService;

	@GetMapping("/{id}/plazodistribucionpermitido")
	public ResponseEntity<AreaPlazoDistribucion> listarPlazoDistribucionByAreaId(@PathVariable Long id) {
		AreaPlazoDistribucion areaPlazoDistribucion = areaPlazoDistribucionService.listarById(id);
		return new ResponseEntity<AreaPlazoDistribucion>(areaPlazoDistribucion,
				areaPlazoDistribucion == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@PutMapping("/{id}/plazosdistribucion")
	public ResponseEntity<Map<String, Object>> actualizarBuzonPlazoDistribucion(@PathVariable Long id ,@RequestBody String plazo) throws JSONException {
		JSONObject requestJson = new JSONObject(plazo);
		Long plazoId = requestJson.getLong("id");
		AreaPlazoDistribucion areaPlazoDistribucion = new AreaPlazoDistribucion();
		areaPlazoDistribucion.setAreaId(id);
		areaPlazoDistribucion.setPlazoId(plazoId);
		AreaPlazoDistribucion areaPlazoDistribucionActualizado = areaPlazoDistribucionService
				.actualizar(areaPlazoDistribucion);
		return new ResponseEntity<Map<String, Object>>(
				areaPlazoDistribucionActualizado == null ? null
						: areaPlazoDistribucionActualizado.getPlazos(),
				areaPlazoDistribucionActualizado == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll()
			throws IOException, JSONException {

		return new ResponseEntity<Iterable<Map<String, Object>>>(areaService.listarAll(), HttpStatus.OK);
	}
}
