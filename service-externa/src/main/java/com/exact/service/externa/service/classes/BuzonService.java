package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IBuzonEdao;
import com.exact.service.externa.service.interfaces.IBuzonService;

@Service
public class BuzonService implements IBuzonService {
	
	@Autowired
	IBuzonEdao buzonEdao;
	
	@Override
	public Map<String, Object> listarById(Long id) throws IOException, JSONException {
		return buzonEdao.listarById(id);
	}

}
