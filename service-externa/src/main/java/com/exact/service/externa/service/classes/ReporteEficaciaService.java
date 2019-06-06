package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IDocumentoReporteDao;
import com.exact.service.externa.dao.IProveedorDao;
import com.exact.service.externa.entity.DocumentoReporte;
import com.exact.service.externa.entity.PlazoDistribucion;
import com.exact.service.externa.entity.Proveedor;
import com.exact.service.externa.service.interfaces.IReporteEficaciaService;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.ENTREGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.REZAGADO;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.NO_DISTRIBUIBLE;
import static com.exact.service.externa.enumerator.EstadoDocumentoEnum.PENDIENTE_ENTREGA;




@Service
public class ReporteEficaciaService implements IReporteEficaciaService {

	
	@Autowired
	IProveedorDao proveedordao;
	
	@Autowired
	IDocumentoReporteDao reportedao;
	
	
	SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public Map<Integer, Map<Integer, Integer>> eficaciaporproveedor(String fechaIni, String fechaFin)
			throws IOException, JSONException {
		
		Map<Integer,Map<Integer, Integer>> multiMap = new HashMap<>();

		Date dateI= null;
		Date dateF= null;
		
		try {
			dateI = dt.parse(fechaIni);
			dateF = dt.parse(fechaFin); 
		} catch (Exception e) {
			return null;
		}
		
		
		Iterable<DocumentoReporte> entidades = reportedao.buscarvolumenporfechas(dateI,dateF);
		List<DocumentoReporte> reportes = new ArrayList<>();
		reportes = StreamSupport.stream(entidades.spliterator(), false).collect(Collectors.toList());
		int cantidadtotal = reportes.size();
		Iterable<Proveedor> iterableproveedores = proveedordao.findAll();
		List<Proveedor> proveedores = StreamSupport.stream(iterableproveedores.spliterator(), false)
				.collect(Collectors.toList());
		
		for (Proveedor pro : proveedores) {
			int cantidadentregado=0;
			int cantidadrezagado=0;
			int cantidadnodistribuible=0;
			int cantidadpendiente=0;
			
			Map<Integer, Integer> m = new HashMap<Integer, Integer>();
			
			for (DocumentoReporte entidad : reportes) {
				
				if (entidad.getProveedorId() == pro.getId()) {
					
//					switch (feriado.validar(id, fecha1, fecha2)) {
//					case 1:
//						rpta = SINFECHA;
//						break;
//					case 2:
//						rpta = DUPLICADOS;
//						break;
//					case 3:
//						rpta = FECHAINCORRECTA;
//						break;
//					case 4:
//						rpta = FINALINICIO;
//						break;
//					case 5:
//						rpta = NOEXISTE;
//						break;
//					}		
					
				}
			m.put((int) (long)ENTREGADO, cantidadentregado);
			m.put((int) (long)REZAGADO, cantidadrezagado);
			m.put((int) (long)NO_DISTRIBUIBLE, cantidadnodistribuible);
			m.put((int) (long)PENDIENTE_ENTREGA, cantidadpendiente);
			}
			
			multiMap.put((int) (long)pro.getId(), m );
			
		}
		return multiMap;
	}

}
