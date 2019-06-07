package com.exact.service.externa.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.exact.service.externa.service.interfaces.IReporteEficaciaService;
import com.exact.service.externa.service.interfaces.IReporteEficienciaService;
import com.exact.service.externa.service.interfaces.IReporteIndicadorVolumenService;
import com.exact.service.externa.service.interfaces.IReporteVolumenService;

@RestController
@RequestMapping("/reportes")
public class ReporteController {

	@Autowired
	IReporteEficienciaService reporteEficienciaservice;
	
	@Autowired
	IReporteVolumenService reporteservice;
	
	@Autowired
	IReporteIndicadorVolumenService indicadorservice;

	ICargosService cargoservice;

	@Autowired
	IReporteEficaciaService eficaciaservice;
	
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
	public ResponseEntity<Map<Long, Map<String, Integer>>> eficienciaPorCourier(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<String, Integer>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.eficienciaPorCourier(fechaini, fechafin);
		return new ResponseEntity<Map<Long, Map<String, Integer>>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/eficiencia/courierporplazo")
	public ResponseEntity<Map<Long, Map<Long, Map<String, Integer>>>> eficienciaPorCourierPorPlazos(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<String, Integer>>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.eficienciaPorPlazoPorCourier(fechaini, fechafin);
		return new ResponseEntity<Map<Long, Map<Long, Map<String, Integer>>>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/volumen/porsede")
	public ResponseEntity<?> porcentajeporvolumenutd(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer, Map<String, Float>> reportecurier = reporteservice.volumenbyutd(fechaini, fechafin);
		return new ResponseEntity<Map<Integer, Map<String, Float>>>(reportecurier,HttpStatus.OK);
	}	
	
	
	@GetMapping("/volumen/plazodistribucion")
	public ResponseEntity<?> volumenporplazo(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer, Integer>> reportecurier = reporteservice.volumenbyplazo(fechaini, fechafin);
		return new ResponseEntity<Map<Integer,Map<Integer, Integer>>>(reportecurier,HttpStatus.OK);
	}	
	
	
	@GetMapping("/eficacia/proveedor")
	public ResponseEntity<?> eficaciaporproveedor(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer, Integer>> reportecurier = eficaciaservice.eficaciaporproveedor(fechaini, fechafin) ;
		return new ResponseEntity<Map<Integer,Map<Integer, Integer>>>(reportecurier,HttpStatus.OK);
	}	
	
	
	@GetMapping("/indicadorvolumen/utds")
	public ResponseEntity<?> porcentajeporvolumenutds(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer, Integer>>  reportecurier = indicadorservice.IndicadorVolumenGrafico(fechaini, fechafin); 
		return new ResponseEntity<Map<Integer,Map<Integer, Integer>> >(reportecurier,HttpStatus.OK);
	}	
	
	@GetMapping("/eficiencia/{id}/detalleporcourier")
	public ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>> detalleEficienciaPorCourier(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @PathVariable Long id) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<Long, Integer>>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.detalleEficienciaPorCourier(fechaini, fechafin, id);
		return new ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>>(cantidades, HttpStatus.OK);
		
	}
	
	@GetMapping("/cargos/devolucionportipo")
	public ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>> devolucionPorTipoDevolucion(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin) throws IOException, JSONException, ParseException, Exception{
		
		Map<Long, Map<Long, Map<Long, Integer>>> cantidades = new HashMap<>();
		cantidades=cargoservice.devolucionPorTipo(fechaini, fechafin);
		return new ResponseEntity<Map<Long, Map<Long, Map<Long, Integer>>>>(cantidades, HttpStatus.OK);
		
	}
	
}
