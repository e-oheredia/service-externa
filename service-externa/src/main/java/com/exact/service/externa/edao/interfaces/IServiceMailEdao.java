package com.exact.service.externa.edao.interfaces;

import java.io.IOException;

import javax.mail.MessagingException;

import org.apache.http.ParseException;

public interface IServiceMailEdao {

	void enviarMensaje(String to, String subject, String text) throws MessagingException, ParseException, IOException;
}
