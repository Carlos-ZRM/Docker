package com.castellana.ecommerce.repository;

import java.util.List;

import com.castellana.ecommerce.models.ProductoModel;

public interface BuscadorRepository {
	
	List<ProductoModel> search(String cadena);

}
