package com.exact.service.externa.service.interfaces;

import com.exact.service.externa.entity.TipoSeguridad;

public interface ITipoSeguridadService {
	
	public Iterable<TipoSeguridad> listarAll();
	TipoSeguridad guardar(TipoSeguridad tiposeguridad);
	Iterable<TipoSeguridad> listarTipoSeguridadActivos();
}
