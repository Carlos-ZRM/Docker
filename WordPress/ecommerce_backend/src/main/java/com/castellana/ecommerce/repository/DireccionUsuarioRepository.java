package com.castellana.ecommerce.repository;

import java.util.List;

import com.castellana.ecommerce.models.DireccionUsuarioModel;

public interface DireccionUsuarioRepository {
	
	int addDireccion(DireccionUsuarioModel dir);
	List<DireccionUsuarioModel> getDirecciones(int idUsuario);
	int actualizarBorradoDireccion(int idDireccion);
	DireccionUsuarioModel getDetalleDireccion(int idDireccion);
	int updateDireccion(DireccionUsuarioModel d);
	int existenDirecciones(int idUsuario);
	int validaCP(String CP);
}
