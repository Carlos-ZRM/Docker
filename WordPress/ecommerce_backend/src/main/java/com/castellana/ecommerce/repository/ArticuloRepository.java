package com.castellana.ecommerce.repository;

import java.util.List;

import com.castellana.ecommerce.models.ProductoModel;


public interface ArticuloRepository {
	List<ProductoModel> getArticulos(int categoriaCarrusel);
	List<ProductoModel> getArticulosFiltrados(int idCategoria, int idAniada, int precioMin, int precioMax);
	List<ProductoModel> getPaquetesBodas(int idCategoria);
	ProductoModel getDetalleArticulo(int id);
	List<ProductoModel> getArticulosEnExistencia(int idToken);
	int actualizarStatus(int idToken, int idVenta, int cantidad, String status);
	int validaInventario(int idArt);
	int cantidadArticuloCarrito(int token, int idArt);
}
