package com.exact.service.externa.service.interfaces;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import com.exact.service.externa.entity.ReporteAsignacionPlazo;

public interface IReporteAsignacionPlazoService {

	Iterable<ReporteAsignacionPlazo> listarReportes(String fechaIni, String fechaFin) throws IOException, Exception;
	
}
