package com.exact.service.externa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.entity.AreaPlazoDistribucion;
import com.exact.service.externa.service.interfaces.IAreaPlazoDistribucionService;

@RestController
@RequestMapping("/areas")
public class AreaController {
	
	@Autowired
	IAreaPlazoDistribucionService areaPlazoDistribucionService;
	
	@GetMapping("/{id}/plazodistribucionpermitido")
	public ResponseEntity<AreaPlazoDistribucion> listarPlazoDistribucionByAreaId(@PathVariable Long id){
		AreaPlazoDistribucion areaPlazoDistribucion = areaPlazoDistribucionService.listarById(id);
		return new ResponseEntity<AreaPlazoDistribucion>(areaPlazoDistribucion, areaPlazoDistribucion == null ? HttpStatus.NOT_FOUND : HttpStatus.OK);
	}
	
}
