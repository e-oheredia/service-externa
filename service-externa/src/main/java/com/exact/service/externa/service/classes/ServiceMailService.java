package com.exact.service.externa.service.classes;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exact.service.externa.edao.interfaces.IServiceMailEdao;
import com.exact.service.externa.service.interfaces.IServiceMail;

@Service
public class ServiceMailService implements IServiceMail{

	@Autowired
	IServiceMailEdao serviceEdao;

	@Override
	public void enviarMensaje(String to, String subject, String text)
			throws MessagingException, ParseException, IOException {
		serviceEdao.enviarMensaje(to, subject, text);
		
	}

}
