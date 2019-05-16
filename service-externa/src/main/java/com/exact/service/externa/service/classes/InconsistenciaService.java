package com.exact.service.externa.service.classes;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IInconsistenciaDao;
import com.exact.service.externa.entity.EnvioMasivo;
import com.exact.service.externa.entity.Inconsistencia;
import com.exact.service.externa.service.interfaces.IInconsistenciaService;

@Service
public class InconsistenciaService implements IInconsistenciaService{

	@Autowired
	IInconsistenciaDao inconsistenciaDao;
	
	@Override
	public void guardarInconsistencias(List<Inconsistencia> inconsistencias, EnvioMasivo envio) {
		
		for (Inconsistencia iconsistente : inconsistencias) {	
			iconsistente.setEnvio(envio.getId());
			inconsistenciaDao.saveAll(inconsistencias);
		}
		
	}

	@Override
	public Iterable<Inconsistencia> listarInconsistenciasPorEnvioId(Long envioId) {
		return inconsistenciaDao.findInconsistenciasByEnvioId(envioId);
	}

}
