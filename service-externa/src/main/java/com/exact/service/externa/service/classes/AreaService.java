package com.exact.service.externa.service.classes;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IAreaEdao;
import com.exact.service.externa.service.interfaces.IAreaService;

@Service
public class AreaService implements IAreaService{

	@Autowired
	IAreaEdao areaEdao;
	
	public Iterable<Map<String, Object>> listarAll() throws IOException, JSONException{
		return areaEdao.listarAll();
	}
}
