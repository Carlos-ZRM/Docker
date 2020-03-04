package com.castellana.ecommerce.repository;

import java.util.List;

import com.castellana.ecommerce.models.ProductoCarritoModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.dto.BotellasCalculoEventoDto;

public interface UsuarioRepository {
	
	int registraUsuario(UsuarioModel usuario);
	int verificaCorreo(String correo);
	UsuarioModel datosUsuario(String correo);
	int updatePasswordUsuario(int idUsuario, String newPassword);
	int suscripcionActivaNewsletter(String correo);
	int insertarUsuarioNL(String mail);
	List<ProductoCarritoModel> existeEnCarrito(int idToken, int idArticulo);
	int incrementaCantidadArticulosCarrito(int idTocken, int idArticulo, int idCantidad);
	int insertarArticuloCarrito(int idToken, int idArticulo, int cantidad);
	int deleteArticuloCarrito(int idToken, int idVenta);
	int actualizarCantidadArticuloCarrito(int idVenta, int cantidad);
	List<ProductoModel> getArticulosCarrito(int token);
	BotellasCalculoEventoDto getBotellasCalculoEvento(int numPer);

}
