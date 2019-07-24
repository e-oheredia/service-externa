package com.exact.service.externa.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.exact.service.externa.entity.TipoSeguridad;
import com.exact.service.externa.model.MTipoSeguridad;

@Component
public class convertertiposeguridad {

	public List<MTipoSeguridad> convertirLista(List<TipoSeguridad> tiposeguridad){
		List<MTipoSeguridad> mtiposeguridad = new ArrayList<>();
		for(TipoSeguridad ts : tiposeguridad) {
			mtiposeguridad.add(new MTipoSeguridad(ts));
		}
		return mtiposeguridad;
	}
	
	public TipoSeguridad convertirETipoSeguridad(MTipoSeguridad mtiposeguridad) {
		TipoSeguridad ts = new TipoSeguridad();
		ts.setId(mtiposeguridad.getId());
		ts.setNombre(mtiposeguridad.getNombre());
		ts.setActivo(mtiposeguridad.isActivo());
		return ts;
	}
	
}
