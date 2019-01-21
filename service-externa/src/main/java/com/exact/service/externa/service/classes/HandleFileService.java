package com.exact.service.externa.service.classes;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import com.exact.service.externa.edao.interfaces.IHandleFileEdao;
import com.exact.service.externa.service.interfaces.IHandleFileService;

public class HandleFileService implements IHandleFileService {
	
	@Autowired
	IHandleFileEdao handleFileEdao;
	
	@Override
	public int upload(MultipartFile file, String ruta) throws IOException {
		return handleFileEdao.upload(file,ruta);
	}

}
