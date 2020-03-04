package com.castellana.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.dto.ParametrosValCompProdDTO;
import com.castellana.ecommerce.dto.ProductoValidadoDto;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.ArticuloRepository;
import com.castellana.ecommerce.repository.ValidacionRepository;
import com.castellana.ecommerce.service.ValidacionService;

@Service
public class ValidacionServicesImpl implements ValidacionService{
	
	@Autowired
	private ValidacionRepository valRep;
	
	@Autowired
	private ArticuloRepository artRep;
	
	private final static Logger LOG = LoggerFactory.getLogger(ArticuloServiceImpl.class);

	@Override
	public List<ProductoModel> validacionProducto(ParametrosValCompProdDTO parametros) {
		List<ProductoModel> lp = new ArrayList<ProductoModel>();
		ProductoModel pro;
		List<Integer> list = new ArrayList<Integer>();
		List<Integer> listIdPro = new ArrayList<Integer>();
		List<ProductoValidadoDto> listPro = valRep.validacionProducto(parametros);
		
		LOG.info("PRODUCTOS COMPRADOS PREVIAMENTE" + listPro);
		for(int i = 0; i<listPro.size(); i++) {
			listIdPro.add(listPro.get(i).getIdArticulo());
		}
		LOG.info("ID PRODUCTOS COMPRADOS PREVIAMENTE" + listPro);
		
		List<Integer> listToken = valRep.productosCarrito(parametros.getIdToken());
		LOG.info("PRODUCTOS EN EL CARRITO: " + listToken);
		
		for(int i = 0; i<listToken.size(); i++ ) {
			if(listIdPro.contains(listToken.get(i))) {
				if(list.contains(listToken.get(i))) {
					LOG.info("YA ESTA inserteado el articulo con id: " + listToken.get(i));
				} else {
					list.add(listToken.get(i));
				}
			}
		}
		
		for(int i = 0; i <list.size(); i++) {
			pro = artRep.getDetalleArticulo(list.get(i));
			LOG.info("ARTICULO " + i + ": " + pro);
			lp.add(pro);
		}
		
		for(int j = 0; j<lp.size(); j++) {
			LOG.info("****************************************************** ENTRA "+ j+1+ " vuelta");
			int delete = valRep.deleteArticuloValidacion(parametros.getIdToken(), list.get(j));
			LOG.info(delete + " EL ARTICULO "+ lp.get(j).getDescripcion() + " se elimino del carrito con token:  "+ parametros.getIdToken());
		}
		
		LOG.info("LISTA: " + lp);
		return lp;
	}

}
