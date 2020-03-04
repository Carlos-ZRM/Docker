package com.castellana.ecommerce.repository;

import com.castellana.ecommerce.models.UsuarioModel;

public interface AuthRepository {
	UsuarioModel loadByName (String usr);
	UsuarioModel login(String usr, String psw);
	int exist(String user);
	boolean oldpwdCorrecta(String usr, String oldpwd);
}
