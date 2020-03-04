package com.castellana.ecommerce.service.impl;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.castellana.ecommerce.service.PasswordFactoryService;

@Service
public class PasswordFactoryServiceImpl implements PasswordFactoryService {

	private final int passwordLength = 8;
	
	private final char[] subset = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ#$%&".toCharArray();
	
	@Override
	public String generarNuevaPassword() {
		Random r = new Random();
		char buf[] = new char[passwordLength];
		
		for (int i = 0; i<buf.length; i++) {
			int index = r.nextInt(subset.length);
			buf[i] = subset[index];
		}
		return new String(buf);
	}
	
}
