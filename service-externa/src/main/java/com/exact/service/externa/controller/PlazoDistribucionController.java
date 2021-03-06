package com.exact.service.externa.controller;

import java.io.IOException;

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

import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.ReporteAsignacionPlazo;
import com.exact.service.externa.service.interfaces.IPlazoDistribucionService;
import com.exact.service.externa.service.interfaces.IReporteAsignacionPlazoService;

@RestController
@RequestMapping("/plazosdistribucion")
public class PlazoDistribucionController {
	
	@Autowired
	IPlazoDistribucionService plazoDistribucionService;
	
	@Autowired
	IReporteAsignacionPlazoService reporteAsignacionPlazoService;
	
	@GetMapping("/activos")
	public ResponseEntity<Iterable<PlazoDistribucion>> listarPlazosActivos() {
		return new ResponseEntity<Iterable<PlazoDistribucion>>(plazoDistribucionService.listarPlazosActivos(), HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<PlazoDistribucion> guardar(@RequestBody PlazoDistribucion plazoDist) {
		plazoDist.setActivo(true);
		try {
			PlazoDistribucion pd = plazoDistribucionService.guardar(plazoDist);
			if(pd==null) {
				return new ResponseEntity<PlazoDistribucion>(HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<PlazoDistribucion>(pd, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<PlazoDistribucion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PlazoDistribucion> modificar(@PathVariable Long id, @RequestBody PlazoDistribucion plazoDist) {
		plazoDist.setId(id);
		try {
			return new ResponseEntity<PlazoDistribucion>(plazoDistribucionService.modificar(plazoDist), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<PlazoDistribucion>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping()
	public ResponseEntity<Iterable<PlazoDistribucion>> listarPlazos() throws Exception {
		return new ResponseEntity<Iterable<PlazoDistribucion>>(plazoDistribucionService.listarAll(), HttpStatus.OK);
	}
	
	@GetMapping("/reporteplazos")
	public ResponseEntity<Iterable<ReporteAsignacionPlazo>> listarAreaBuzonPlazos(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, Exception {
		return new ResponseEntity<Iterable<ReporteAsignacionPlazo>>(reporteAsignacionPlazoService.listarReportes(fechaini, fechafin), HttpStatus.OK);
	}
	
	@GetMapping("{id}/plazoregion")
	public ResponseEntity<Iterable<PlazoDistribucion>> listarAreaBuzonPlazos(@PathVariable Long id) throws IOException, Exception {
		return new ResponseEntity<Iterable<PlazoDistribucion>>(plazoDistribucionService.listarPlazosByRegionId(id), HttpStatus.OK);
	}
	
	@GetMapping("{id}/distrito")
	public ResponseEntity<Iterable<PlazoDistribucion>> listarPlazosPorDistrito(@PathVariable Long id) throws IOException, Exception {
		return new ResponseEntity<Iterable<PlazoDistribucion>>(plazoDistribucionService.listarPlazosByDistritoId(id), HttpStatus.OK);
	}
	
}
