package com.castellana.ecommerce.service.impl;

import javax.naming.AuthenticationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.dto.LoginOutDto;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.repository.AuthRepository;
import com.castellana.ecommerce.security.JwtUtil;
import com.castellana.ecommerce.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	AuthRepository authRepository;

	@Override
	public int existUsuario(String user) {
		return authRepository.exist(user);
	}

	@Override
	public UsuarioModel loadByName(String user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LoginOutDto auth(String user, String pass) throws AuthenticationException {
		UsuarioModel usr = authRepository.loadByName(user);
		
		if (user == null) {
			logger.info("ERROR: INICIO DE SESION DENEGADO USUARIO NO REGISTRADO");
			throw new AuthenticationException("ERROR: USUARIO NO REGISTRADO");
		}
		logger.info("PSW input: 0" + pass);
		logger.info("PSW base: 0" + usr.getPassword());
		String genPassword = DigestUtils.sha1Hex(pass);
		
		logger.info("gen: " + genPassword);
		logger.info("user: " + usr.getPassword());//aparece nulo
		
		if(genPassword.equals(usr.getPassword())) {
			usr.setPassword("");
			String token = jwtUtil.generateJwt(usr);
			logger.info("Inicio de sesion autorizado");
			LoginOutDto userDto = new LoginOutDto(token);
			userDto.setUsuario(usr);
			return userDto;
		} else {
			logger.info("ERROR: INICIO DE SESION DENEGADO CONTRASENIA INCORRECTA");
			throw new IllegalArgumentException("CONTRASENIA INCORRECTA");
		}
	}

	@Override
	public void registerLogin(int idUser, String token, UsuarioModel usuarioModel) {
		if(usuarioModel.getBrowserMajorVersion().toLowerCase().contains("unknown")) {
			usuarioModel.setBrowserMajorVersion("0");
		}
		if(usuarioModel.getBrowserMajorVersion().toLowerCase().contains("unknown")) {
			usuarioModel.setPlatformVersion("0");
		}
		
	}

	@Override
	public void registerLogout(int idUser, String auth, UsuarioModel userAgentModel) {
		// TODO Auto-generated method stub
		
	}

}
