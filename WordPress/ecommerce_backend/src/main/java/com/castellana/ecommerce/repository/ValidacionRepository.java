package com.castellana.ecommerce.repository;

import java.util.List;

import com.castellana.ecommerce.dto.ParametrosValCompProdDTO;
import com.castellana.ecommerce.dto.ProductoValidadoDto;

public interface ValidacionRepository {
	
	List<ProductoValidadoDto> validacionProducto(ParametrosValCompProdDTO parametros);
	List<Integer> productosCarrito(int idToken);
	int deleteArticuloValidacion(int idToken, int idArticulo);
}
