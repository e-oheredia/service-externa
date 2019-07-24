package com.exact.service.externa.controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
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
import com.exact.service.externa.service.interfaces.IRegionService;
import com.exact.service.externa.service.interfaces.IAmbitoService;
import com.exact.service.externa.service.interfaces.IDiaService;
import com.exact.service.externa.service.interfaces.IFeriadoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RestController
@RequestMapping("/regiones")
public class RegionController {
	
	@Autowired
	IAmbitoService ambitoService;
	
	@Autowired
	IRegionService regionservice;
	
	@Autowired
	IFeriadoService feriadoservice;
	
	@Autowired
	IDiaService diaservice;

	@GetMapping()
	public ResponseEntity<Iterable<Map<String, Object>>> listarRegiones() throws Exception {
		return new ResponseEntity<>(regionservice.listardiaslaborables(), HttpStatus.OK);
	}
	
	@PutMapping("/{id}/diaslaborables")
	public ResponseEntity<Map<String, Object>> modificarDias(@PathVariable Long id,@RequestBody String ambito) throws Exception {
	
		Map<String,Object> ambitoModificado = regionservice.modificarRegion(id,ambito);
		
		int rpta = (int) ambitoModificado.get("responsecode");
		
		if(rpta==400) {
			return new ResponseEntity<Map<String, Object>>(ambitoModificado, HttpStatus.BAD_REQUEST);
		}else {
			return new ResponseEntity<Map<String, Object>>(ambitoModificado, HttpStatus.OK);
		} 
		
	}
	
	@GetMapping("/ambitos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarAllAmbitos() throws IOException, JSONException, Exception {
		return new ResponseEntity<>(regionservice.listarAmbitos(), HttpStatus.OK);
	}
	
	@GetMapping("/{id}/ambitos/activos")
	public ResponseEntity<Iterable<Map<String, Object>>> listarAmbitosActivosByAmbitoId(@PathVariable Long id) throws Exception{
		return new ResponseEntity<>(regionservice.listarAmbitosByAmbitoId(id) , HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<Map<String, Object>> guardarAmbito(@RequestBody String subambito) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			Map<String,Object> ambitoNuevo = regionservice.guardarAmbito(subambito);
			int rpta = (int) ambitoNuevo.get("responsecode");
			
			if(rpta==400) {
				return new ResponseEntity<>(ambitoNuevo, HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<>(ambitoNuevo, HttpStatus.OK);
			} 
		} catch (Exception e) {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping("/{id}/ambitos")
	public ResponseEntity<Map<String, Object>> modificarAmbito(@PathVariable Long id, @RequestBody String subambito) throws  Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		try {
			Map<String,Object> ambitoModificado = regionservice.modificarAmbito(id, subambito);
			
			int rpta = (int) ambitoModificado.get("responsecode");
			
			if(rpta==400) {
				return new ResponseEntity<>(ambitoModificado, HttpStatus.BAD_REQUEST);
			}else {
				return new ResponseEntity<>(ambitoModificado, HttpStatus.OK);
			} 
			
		} catch (Exception e) {
			return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
		
	@GetMapping("/{id}/diaslaborales")
	public ResponseEntity<Iterable<Map<String, Object>>> listarDiasLaborales(@PathVariable Long id) throws Exception{
		return new ResponseEntity<>(regionservice.listardiaslaborales(id) , HttpStatus.OK);
	}	
	
	@GetMapping("/{id}/horaslaborales")
	public ResponseEntity<Iterable<Map<String, Object>>> listarHorasLaborales(@PathVariable Long id) throws Exception{
		return new ResponseEntity<>(regionservice.listarhoraslaborales(id) , HttpStatus.OK);
	}		
	
	@PostMapping("/{id}/feriados")
	public ResponseEntity<Map<String, Object>> guardarferiado(@PathVariable Long id, @RequestBody String feriado) throws Exception{
		return new ResponseEntity<>(feriadoservice.guardarferiado(id,feriado) , HttpStatus.OK);
	}	
	
	@GetMapping("/{id}/diaslaboralesporrango")
	public ResponseEntity<Map<String, Object>> listardiasporrango(@PathVariable Long id,			
			@RequestParam(value = "fecha1") String fecha1,
			@RequestParam(value = "fecha2") String fecha2) throws Exception{
		return new ResponseEntity<>(diaservice.listarferiados(id, fecha1, fecha2)  , HttpStatus.OK);
	}		
	
}
