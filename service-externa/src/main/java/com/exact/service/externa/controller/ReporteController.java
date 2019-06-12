package com.exact.service.externa.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exact.service.externa.service.interfaces.ICargosService;
import com.exact.service.externa.service.interfaces.IReporteEficienciaService;
import com.exact.service.externa.service.interfaces.IReporteVolumenService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	IReporteEficienciaService reporteEficienciaservice;
	
	@Autowired
	IReporteVolumenService reporteservice;
	
	@Autowired
	ICargosService cargoservice;
	
	@GetMapping("/volumen/curier")
	public ResponseEntity<?> porcentajeporvolumencurier(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Long,Map<String, Float>> reportecurier = reporteservice.volumenbycurier(fechaini, fechafin);
		return new ResponseEntity<Map<Long,Map<String, Float>>>(reportecurier,HttpStatus.OK);
	}

	
	@GetMapping("/eficiencia/porcourier")
	public ResponseEntity<Map<Integer,Object>> eficienciaPorCourier(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<String, Integer>> cantidades = new HashMap<>();
		Map<Long, Map<Long, Map<String, Integer>>> cantidadesPorPlazo = new HashMap<>();
		cantidades=reporteEficienciaservice.eficienciaPorCourier(fechaini, fechafin);
		cantidadesPorPlazo=reporteEficienciaservice.eficienciaPorPlazoPorCourier(fechaini, fechafin);
		Map<Integer,Object> graficoEficiencia = new HashMap<>();
		graficoEficiencia.put(1, cantidades);
		graficoEficiencia.put(2, cantidadesPorPlazo);
		return new ResponseEntity<Map<Integer,Object>>(graficoEficiencia, HttpStatus.OK);
		
	}
	
	@GetMapping("/eficiencia/courierporplazo")
	public ResponseEntity<Map<Long, Map<Long, Map<String, Integer>>>> eficienciaPorCourierPorPlazos(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<String, Integer>>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.eficienciaPorPlazoPorCourier(fechaini, fechafin);
		return new ResponseEntity<Map<Long, Map<Long, Map<String, Integer>>>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/volumen/utd")
	public ResponseEntity<?> porcentajeporvolumenutd(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Long,Map<String, Float>> reportecurier = reporteservice.volumenbyutd(fechaini, fechafin);
		return new ResponseEntity<Map<Long,Map<String, Float>>>(reportecurier,HttpStatus.OK);
	}	
	
	@GetMapping("/eficiencia/{id}/detalleporcourier")
	public ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>> detalleEficienciaPorCourier(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @PathVariable Long id) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<Long, Integer>>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.detalleEficienciaPorCourier(fechaini, fechafin, id);
		return new ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/cargos/devolucionportipo")
	public ResponseEntity<Map<Integer,Object>> devolucionPorTipoDevolucion(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<String, Integer>>> cantidadesProveedor = new HashMap<>();
		Map<Long, Integer> cantidadesPorArea = new HashMap<>();
		cantidadesProveedor=cargoservice.devolucionPorTipo(fechaini, fechafin);
		cantidadesPorArea = cargoservice.pendientesCargosPorArea(fechaini, fechafin);
		
		Map<Integer,Object> graficoControl = new HashMap<>();
		graficoControl.put(1, cantidadesProveedor);
		graficoControl.put(2, cantidadesPorArea);
		return new ResponseEntity<Map<Integer,Object>>(graficoControl, HttpStatus.OK);
		
	}
	
	@GetMapping("/cargos/devolucionporarea")
	public ResponseEntity<Map<Long,Integer>> pendientesDevolucionPorArea(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long,Integer> cantidades = new HashMap<>();
		cantidades=cargoservice.pendientesCargosPorArea(fechaini,fechafin);
		return new ResponseEntity<Map<Long,Integer>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/control/{id}/estado")
	public ResponseEntity<Map<Integer,Object>> controlCargos(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @PathVariable Long id) throws IOException, JSONException, ParseException, Exception{
		Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>> controlCargo = new HashMap<>();
		Map<Long, Map<Integer, Map<Integer, Integer>>> controlPorArea = new HashMap<>();
		Map<Integer,Object> graficoControl = new HashMap<>();
		controlCargo=cargoservice.controlCargos(fechaini, fechafin, id);
		controlPorArea=cargoservice.controlCargosPorAreas(fechaini, fechafin, id);
		graficoControl.put(1, controlCargo);
		graficoControl.put(2, controlPorArea);
		return new ResponseEntity<Map<Integer,Object>>(graficoControl, HttpStatus.OK);
		
	}
	
	
}
