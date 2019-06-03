package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IAreaService;

@RestController
@RequestMapping("/areas")
public class AreaController {@Autowired
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
	public ResponseEntity<PlazoDistribucion> actualizarBuzonPlazoDistribucion(@PathVariable Long id,
			@RequestPart("plazoDistribucion") @Valid PlazoDistribucion plazoDistribucion, @RequestPart("file") MultipartFile file) throws IOException {
		AreaPlazoDistribucion areaPlazoDistribucion = new AreaPlazoDistribucion();
		areaPlazoDistribucion.setPlazoDistribucion(plazoDistribucion);
		areaPlazoDistribucion.setAreaId(id);
		AreaPlazoDistribucion areaPlazoDistribucionActualizado = areaPlazoDistribucionService
				.actualizar(areaPlazoDistribucion,file);
		return new ResponseEntity<PlazoDistribucion>(
				areaPlazoDistribucionActualizado == null ? null
						: areaPlazoDistribucionActualizado.getPlazoDistribucion(),
				areaPlazoDistribucionActualizado == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping()
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll()
			throws IOException, JSONException {

		return new ResponseEntity<Iterable<Map<String, Object>>>(areaService.listarAll(), HttpStatus.OK);
	}
	
	@GetMapping("/areasplazo")
	public ResponseEntity<Iterable<AreaPlazoDistribucion>> listarAreaPlazos()
			throws IOException, JSONException {

		return new ResponseEntity<Iterable<AreaPlazoDistribucion>>(areaPlazoDistribucionService.listarAreaPlazos(), HttpStatus.OK);
	}
	
}
