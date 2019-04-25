package com.exact.service.externa.service.classes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exact.service.externa.dao.IMotivoEstadoDao;
import com.exact.service.externa.edao.interfaces.IMenuEdao;
import com.exact.service.externa.entity.MotivoEstado;
import com.exact.service.externa.service.interfaces.IMotivoEstadoService;

@Service
public class MotivoEstadoService implements IMotivoEstadoService{

	@Autowired
	IMotivoEstadoDao motivodao ;

	
	@Override
	public Iterable<MotivoEstado> listarMotivos() {
		return motivodao.findAll();
	}

}
