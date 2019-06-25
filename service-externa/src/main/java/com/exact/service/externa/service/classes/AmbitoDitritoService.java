package com.exact.service.externa.service.classes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.dao.IAmbitoDistritoDao;
import com.exact.service.externa.entity.AmbitoDistrito;
import com.exact.service.externa.service.interfaces.IAmbitoDistritoService;
 
@Service
public class AmbitoDitritoService implements IAmbitoDistritoService{

	@Autowired
	IAmbitoDistritoDao ambitoDistritoDao;
	
	@Override
	public Iterable<AmbitoDistrito> listarAmbitoDistritos() {
		return null;
	}
	

}
