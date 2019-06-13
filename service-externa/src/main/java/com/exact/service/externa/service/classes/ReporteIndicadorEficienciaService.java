package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteIndicadorEficienciaService;

import static com.exact.service.externa.enumerator.EstadoTiempoEntregaEnum.DENTRO_PLAZO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;

@Service
public class ReporteIndicadorEficienciaService implements IReporteIndicadorEficienciaService {

	@Autowired
	IDocumentoReporteDao documentoreporteDao;
	
	@Autowired
	IProveedorDao proveedorDao;
	
	@Override
	public Map<Integer, Map<Integer, Float>> graficoTablaPorcentaje(String fechaIni, String fechaFin)
			throws IOException, NumberFormatException, ParseException {
		SimpleDateFormat dtmeses = new SimpleDateFormat("MM");		
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM");
		Date dateI= null;
		Date dateF= null;

		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		Iterable<DocumentoReporte> documentos = documentoreporteDao.buscarvolumenporfechas(dateI,dateF);
		List<String> listademeses = new ArrayList<>();
		List<Date> meses = getListaEntreFechas2(dateI,dateF);
		for(Date mess : meses) {
			listademeses.add(dt.format(mess)); 
		}
		int i=0;
		Map<Integer, Map<Integer, Float>> indicesMeses = new HashMap<>();
		for(String mesaño : listademeses) {
			int cantidadTotal =0;
			int totalmes = 0;
			float porcentaje =(float)0.0;
			Map<Integer,Float> mesesTotal = new HashMap<>();
			for(DocumentoReporte documentoreporte : documentos) {
				if(dt.format(documentoreporte.getFecha()).equals(mesaño)) {
					if(documentoreporte.getEstadoDocumento()==ENTREGADO) {
						if(documentoreporte.getTiempoEntrega()==DENTRO_PLAZO) {
							cantidadTotal++;
						}
					}
					totalmes++;
				}
			}
			i++;
			if(totalmes!=0) {
				porcentaje = ((float) cantidadTotal / totalmes )* 100;
				
			}else {
				porcentaje = 0;
			}
			mesesTotal.put(Integer.parseInt(dtmeses.format(dt.parse(mesaño))), porcentaje);
			indicesMeses.put(i, mesesTotal);
			
		}
		return indicesMeses;
	}
	
	public List<Date> getListaEntreFechas2(Date fechaInicio, Date fechaFin) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(fechaInicio);
		Calendar c2 = Calendar.getInstance();
		c2.setTime(fechaFin);
		java.util.List<Date> listaFechas = new java.util.ArrayList<Date>();

		while (!c1.after(c2)) {
			listaFechas.add(c1.getTime());
			c1.add(Calendar.MONTH , 1);
		}
		return listaFechas;
	}

	@Override
	public Map<Long, Map<Integer, Map<Integer, Map<Long, Map<String, Integer>>>>> proveedorPlazoDentroFuera(String fechaini, String fechafin) throws IOException, NumberFormatException, ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
