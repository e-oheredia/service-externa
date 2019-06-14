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
import com.exact.service.externa.service.interfaces.IReporteIndicadorEficacia;
import com.exact.service.externa.service.interfaces.IReporteIndicadorEficienciaService;
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

	@Autowired
	ICargosService cargoservice;

	@Autowired
	IReporteEficaciaService eficaciaservice;
	
	@Autowired
	IReporteIndicadorEficacia indicadoreficaciaservice;
	
	@Autowired
	IReporteIndicadorEficienciaService indicadoreficienciaservice;
	
	@GetMapping("/volumen/curier")
	public ResponseEntity<?> porcentajeporvolumencurier(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException {
		Map<Integer, Object> nuevo = new HashMap<>();
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Long,Map<String, Float>> reportecurier = reporteservice.volumenbycurier(fechaini, fechafin);
		Map<Integer, Map<String, Float>> reportecurier2 = reporteservice.volumenbyutd(fechaini, fechafin);
		Map<Integer,Map<Integer, Integer>> reportecurier3 = reporteservice.volumenbyplazo(fechaini, fechafin);
		nuevo.put(1, reportecurier);
		nuevo.put(2, reportecurier2);
		nuevo.put(3, reportecurier3);
		return new ResponseEntity<Map<Integer, Object>>(nuevo,HttpStatus.OK);
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
	
	
	@GetMapping("/indicadorvolumen")
	public ResponseEntity<?> indicadorvolumentabla3(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		Map<Integer, Object> nuevo = new HashMap<>();
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer, Integer>>  reportecurier = indicadorservice.IndicadorVolumenGrafico(fechaini, fechafin);
		Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>>   reportecurier2 = indicadorservice.IndicadorVolumenTabla2(fechaini,fechafin);
		Map<Integer, Map<Integer, Map<Integer, Integer>>> reportecurier3 = indicadorservice.indicadortabla2cabeceravolumen(fechaini, fechafin); 
		nuevo.put(1, reportecurier);
		nuevo.put(2, reportecurier2);
		nuevo.put(3, reportecurier3);
		return new ResponseEntity<Map<Integer, Object>>(nuevo,HttpStatus.OK);
	}	
	
	@GetMapping("/indicadorvolumen/tabla2")
	public ResponseEntity<?> indicadorvolumentabla2(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>>   reportecurier = indicadorservice.IndicadorVolumenTabla2(fechaini,fechafin);
		return new ResponseEntity<Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>> >(reportecurier,HttpStatus.OK);
	}		
	
	@GetMapping("/indicadoreficacia")
	public ResponseEntity<?> indicadoreficacia(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		Map<Integer, Object> nuevo = new HashMap<>();
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer, Map<Integer, Float>> reportecurier = indicadoreficaciaservice.indicadorgrafico(fechaini, fechafin);
		Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>> reportecurier2 = indicadoreficaciaservice.indicadortabla2(fechaini, fechafin); 	
		Map<Integer, Map<Integer, Map<Integer, Float>>> reportecurier3 = indicadoreficaciaservice.indicadortabla2cabecera(fechaini, fechafin); 
		nuevo.put(1, reportecurier);
		nuevo.put(2, reportecurier2);
		nuevo.put(3, reportecurier3);		
		return new ResponseEntity<Map<Integer, Object>>(nuevo,HttpStatus.OK);
	}	
	
	
	@GetMapping("/indicadoreficacia/tabla2")
	public ResponseEntity<?> indicadoreficacia2(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>> reportecurier = indicadoreficaciaservice.indicadortabla2(fechaini, fechafin); 
		return new ResponseEntity<Map<Integer,Map<Integer,Map<Integer,  Map<Integer, Integer>>>>>(reportecurier,HttpStatus.OK);
	}		
	
	@GetMapping("/eficiencia/{id}/detalleporcourier")
	public ResponseEntity<?> detalleEficienciaPorCourier(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin, @PathVariable Long id) throws IOException, JSONException, ParseException, Exception{
		Map<Long, Map<String, Integer>> cantidades = new HashMap<>();
		cantidades=reporteEficienciaservice.detalleEficienciaPorCourier(fechaini, fechafin, id);
		return new ResponseEntity<Map<Long, Map<String, Integer>>>(cantidades, HttpStatus.OK);
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

	@GetMapping("/indicadoreficacia/tabla2cabecera")
	public ResponseEntity<?> indicadoreficacia2cabecera(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer, Map<Integer, Map<Integer, Float>>> reportecurier = indicadoreficaciaservice.indicadortabla2cabecera(fechaini, fechafin); 
		return new ResponseEntity<Map<Integer, Map<Integer, Map<Integer, Float>>>>(reportecurier,HttpStatus.OK);
	}
	
	@GetMapping("/indicadorvolumen/tabla2cabecera")
	public ResponseEntity<?> indicadoreficacia2cabeceravaolumen(@RequestParam(name="fechaini", required=false) String fechaini, @RequestParam(name="fechafin",required=false) String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer, Map<Integer, Map<Integer, Integer>>> reportecurier = indicadorservice.indicadortabla2cabeceravolumen(fechaini, fechafin); 
		return new ResponseEntity<Map<Integer, Map<Integer, Map<Integer, Integer>>>>(reportecurier,HttpStatus.OK);
	}	
	
	@GetMapping("/indicadoreficiencia/graficotabla")
	public ResponseEntity<?> indicadoreficienciagraficotabala(@RequestParam(name="fechaini") String fechaini, @RequestParam(name="fechafin") String fechafin)
			throws IOException, JSONException, NumberFormatException, ParseException {
		if(fechaini=="" || fechafin==""){
			return new ResponseEntity<String>("Valores de fecha incompletos", HttpStatus.BAD_REQUEST);
		}
		Map<Integer, Map<Integer, Float>> reportegrafico1 = indicadoreficienciaservice.graficoTablaPorcentaje(fechaini, fechafin);
		Map<Long, Map<Long, Map<Integer, Map<Integer, Map<String, Integer>>>>> reportegrafico2 = indicadoreficienciaservice.proveedorPlazoDentroFuera(fechaini, fechafin);
		Map<Integer,Object> graficoIndicador = new HashMap<>();
		graficoIndicador.put(1, reportegrafico1);
		graficoIndicador.put(2, reportegrafico2);
		return new ResponseEntity<Map<Integer,Object>>(graficoIndicador,HttpStatus.OK);
	}
	
	
	
	
}
