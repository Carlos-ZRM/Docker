package com.castellana.ecommerce.controller;

import java.io.IOException;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.LoginInDto;
import com.castellana.ecommerce.dto.LoginOutDto;
import com.castellana.ecommerce.service.LoginService;

@RestController
@RequestMapping("api/auth")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	
	@Autowired
	LoginService loginService;
	
	@ExceptionHandler({ IllegalArgumentException.class, NullPointerException.class })
	void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}
	
	@ExceptionHandler({AuthenticationException.class})
	void handlerAuthenticationException(AuthenticationException e, HttpServletResponse response) throws IOException {
		logger.info("" + HttpStatus.UNAUTHORIZED.value());
		response.sendError(HttpStatus.UNAUTHORIZED.value());
	}

	@PostMapping("/login/")
	public LoginOutDto login(@RequestBody LoginInDto usuario) throws AuthenticationException {
		logger.info("USUARIO = " + usuario.getUser());
		logger.info("PASSWORD = " + usuario.getPassword());
		return loginService.auth(usuario.getUser(), usuario.getPassword());
	}
}
