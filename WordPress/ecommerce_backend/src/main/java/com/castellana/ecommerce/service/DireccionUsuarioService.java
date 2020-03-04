package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.models.DireccionUsuarioModel;

public interface DireccionUsuarioService {
	boolean addDireccion(DireccionUsuarioModel direccion);
	List<DireccionUsuarioModel> getDirecciones(int idUsuario);
	boolean actualizarBorradoDireccion(int idDireccion);
	DireccionUsuarioModel getDetalleDireccion(int idDireccion);
	boolean updateDireccion(DireccionUsuarioModel d);
	boolean existenciaDireccion(int idUsuario);
	boolean validaCP(String CP);
}
