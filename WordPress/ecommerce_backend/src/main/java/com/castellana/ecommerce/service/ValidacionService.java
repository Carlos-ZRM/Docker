package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.dto.ParametrosValCompProdDTO;
import com.castellana.ecommerce.models.ProductoModel;

public interface ValidacionService {
	
	List<ProductoModel> validacionProducto(ParametrosValCompProdDTO parametros);

}
