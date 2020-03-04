package com.castellana.ecommerce.service;

import javax.naming.AuthenticationException;

import com.castellana.ecommerce.dto.LoginOutDto;
import com.castellana.ecommerce.models.UsuarioModel;

public interface LoginService {
	int existUsuario(String user);
	UsuarioModel loadByName(String user);
	LoginOutDto auth(String user, String pass) throws AuthenticationException;
	void registerLogin(int idUser, String token, UsuarioModel userAgentModel);
	void registerLogout(int idUser, String auth, UsuarioModel userAgentModel);
}
