package com.castellana.ecommerce.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.BuscadorRepository;
import com.castellana.ecommerce.service.BuscadorService;

@Service
public class BuscadorServiceImpl  implements BuscadorService{
	
	@Autowired
	private BuscadorRepository buscador; 

	@Override
	public List<ProductoModel> searchForThypeahead(String stringSearch) {
		List<ProductoModel> productos = buscador.search(stringSearch);
		return productos;
	}

}
