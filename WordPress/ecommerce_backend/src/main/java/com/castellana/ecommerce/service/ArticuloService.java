package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.models.ProductoModel;

public interface ArticuloService {
	List<ProductoModel> getArticulos(int categoriaCarrusel);
	List<ProductoModel> filtroArticulos(int idCategoria, Integer idAniada, Integer precioMin, Integer precioMax);
	List<ProductoModel> productoPaqueteBodas(int idCat);
	ProductoModel getDetalleProducto(int id);
	List<Object> verificaExistenciaProductos(int idToken);
	int validaInventario(int idArt);
	int cantidadArticuloCarrito(int token, int idArt);
 }
