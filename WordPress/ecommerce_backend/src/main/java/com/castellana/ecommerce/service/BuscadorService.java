package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.models.ProductoModel;

public interface BuscadorService {
	
	List<ProductoModel> searchForThypeahead(String stringSearch);

}
