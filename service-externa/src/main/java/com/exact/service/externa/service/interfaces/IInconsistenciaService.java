package com.exact.service.externa.service.interfaces;


import java.util.List;

import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Inconsistencia;

public interface IInconsistenciaService {

	void guardarInconsistencias(List<Inconsistencia> inconsistencialst, EnvioMasivo envio);
	public Iterable<Inconsistencia> listarInconsistenciasPorEnvioId(Long envioId);
}
