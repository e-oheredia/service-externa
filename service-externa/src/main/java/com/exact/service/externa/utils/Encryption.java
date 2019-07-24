package com.exact.service.externa.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class Encryption {
	
	@Value("${key.string}")
	String rutaLlave;	

	private static final Log Logger = LogFactory.getLog(Encryption.class);

	
	//5852459632586596

	private  final String initVector = "5865214578523659";
	public String encrypt(String value) throws IOException {
		String key = "";
		File archivo = new File (rutaLlave);
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			key = br.readLine();
		} finally {
			if(br!=null) {
				try {
					br.close();
				} catch (Exception e) {
					Logger.info("ERROR");				
					}
			}
			if(fr!=null) {
				try {
					fr.close();
				} catch (Exception e) {
					Logger.info("ERROR");				
				}
			}
		}
		byte[] keybytes = key.getBytes("UTF-8");

	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return Base64.getEncoder().encodeToString(encrypted);
	    } catch (Exception ex) {
			Logger.info("ERROR");				
	    }
	    return null;
	}
	
	public String decrypt(String encrypted) throws IOException {
		String key = "";
		File archivo = new File (rutaLlave);
		FileReader fr = null;
		BufferedReader br = null;
		try{
			fr = new FileReader (archivo);
			br = new BufferedReader(fr);
			key = br.readLine();
		} finally {
			if(br!=null) {
				try {
					br.close();
				} catch (Exception e) {
					Logger.info("ERROR");				
				}
			}
			if(fr!=null) {
				try {
					fr.close();
				} catch (Exception e) {
					Logger.info("ERROR");				
				}
			}
		}
		byte[] keybytes = key.getBytes("UTF-8");
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(keybytes, "AES");	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encrypted));
	 
	        return new String(original);
	    } catch (Exception ex) {
			Logger.info("ERROR");				
	    }
	 
	    return null;
	}
}