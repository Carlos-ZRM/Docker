package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.dto.ChangePasswordDto;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.dto.BotellasCalculoEventoDto;

public interface UsuarioService {
	int registraUsuario(UsuarioModel usuario);
	boolean verificaCorreo(String correo);
	int ressetPassword(String correo);
	int suscripcionNewslatter(String correo);
	boolean correoContacto(String nombre, String mail, String mensaje);
	List<Integer> insertarArticuloCarrito(int idToken, int idArticulo, int cantidad);
	boolean eliminaArticuloCarrito(int idToken, int idVenta);
	List<Integer> actualizarCantidadArticuloCarrito(int idToken, int idVenta, int cantidad, int idArticulo);
	List<ProductoModel> getArticulosCarrito(int token);
	int cambioPassword(ChangePasswordDto update);
	BotellasCalculoEventoDto calculaEvento(int numP);
}

