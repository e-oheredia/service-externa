package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.BuzonPlazoDistribucion;
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
	public ResponseEntity<Map<String, Object>> listarBuzonById(@PathVariable Long id) throws IOException, JSONException{
		Map<String, Object> buzon = buzonService.listarById(id);
		return new ResponseEntity<Map<String,Object>>(buzon, buzon == null ? HttpStatus.NOT_FOUND: HttpStatus.OK);
	}
	
	@GetMapping("/{id}/plazodistribucionpermitido")
	public ResponseEntity<BuzonPlazoDistribucion> listarPlazoDistribucionByBuzonId(@PathVariable Long id){
		BuzonPlazoDistribucion buzonPlazoDistribucion = buzonPlazoDistribucionService.listarById(id);
		return new ResponseEntity<BuzonPlazoDistribucion>
		(buzonPlazoDistribucion, buzonPlazoDistribucion == null ? HttpStatus.NOT_FOUND: HttpStatus.OK);
	}
	
}
