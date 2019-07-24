package com.exact.service.externa.service.interfaces;

import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.model.MTipoSeguridad;

public interface ITipoSeguridadService {
	
	public Iterable<TipoSeguridad> listarAll();
	MTipoSeguridad guardar(TipoSeguridad tiposeguridad);
	Iterable<MTipoSeguridad> listarTipoSeguridadActivos();
}
