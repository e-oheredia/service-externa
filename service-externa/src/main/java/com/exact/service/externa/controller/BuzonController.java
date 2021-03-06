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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.service.interfaces.IBuzonPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IBuzonService;

@RestController
@RequestMapping("/buzones")
public class BuzonController {
	
	@Autowired
	IBuzonService buzonService;

	@Autowired
	IBuzonPlazoDistribucionService buzonPlazoDistribucionService;

	@GetMapping("/{id}")
	public ResponseEntity<Map<String, Object>> listarBuzonById(@PathVariable Long id)
			throws IOException, JSONException {
		Map<String, Object> buzon = buzonService.listarById(id);
		return new ResponseEntity<>(buzon, buzon == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	@GetMapping("/{id}/plazodistribucionpermitido")
	public ResponseEntity<BuzonPlazoDistribucion> listarPlazoDistribucionByBuzonId(@PathVariable Long id) {
		BuzonPlazoDistribucion buzonPlazoDistribucion = buzonPlazoDistribucionService.listarById(id);
		return new ResponseEntity<>(buzonPlazoDistribucion,
				buzonPlazoDistribucion == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}

	
	@PutMapping("/{id}/plazosdistribucion")
	public ResponseEntity<PlazoDistribucion> actualizarBuzonPlazoDistribucion(@PathVariable Long id,
			@RequestPart("plazoDistribucion") @Valid PlazoDistribucion plazoDistribucion, @RequestPart("file") MultipartFile file) throws IOException {
		BuzonPlazoDistribucion buzonPlazoDistribucion = new BuzonPlazoDistribucion();
		buzonPlazoDistribucion.setPlazoDistribucion(plazoDistribucion);
		buzonPlazoDistribucion.setBuzonId(id);
		BuzonPlazoDistribucion buzonPlazoDistribucionActualizado = buzonPlazoDistribucionService.actualizar(buzonPlazoDistribucion,file);
		if(buzonPlazoDistribucionActualizado==null) {
			return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(buzonPlazoDistribucionActualizado.getPlazoDistribucion(),HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<Iterable<Map<String, Object>>> listarAll()
			throws IOException, JSONException {

		return new ResponseEntity<>(buzonService.listarAll(), HttpStatus.OK);
	}
	
	@GetMapping("/buzonesplazo")
	public ResponseEntity<Iterable<BuzonPlazoDistribucion>> listarAllBuzonPlazo()
			throws IOException, JSONException {

		return new ResponseEntity<>(buzonPlazoDistribucionService.listarBuzonPlazo(), HttpStatus.OK);
	}
	
}
