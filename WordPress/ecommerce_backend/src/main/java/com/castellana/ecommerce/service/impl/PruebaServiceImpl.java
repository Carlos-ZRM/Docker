package com.castellana.ecommerce.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.Customer.CustomerResponseDto;
import com.castellana.ecommerce.externos.MercadoPagoComponent;
import com.castellana.ecommerce.repository.PruebaRepository;
import com.castellana.ecommerce.service.PruebaService;

@Service
public class PruebaServiceImpl implements PruebaService{
	
	public static final Logger LOG = LoggerFactory.getLogger(PruebaServiceImpl.class);
	
	@Autowired
	PruebaRepository pruebaRepository;
	
	@Autowired
	MercadoPagoComponent mercadoPago;

	@Override
	public int insertaVenta(VentaDto venta) {

		int respuesta = pruebaRepository.insertVenta(venta);
		LOG.info("RESPUESTA: " + respuesta);
		return respuesta;
	}

	@Override
	public int usuarioMercadoPago(String mail) {
		CustomerResponseDto custumer =  mercadoPago.regresaStatusRegistradoUsuario(mail);
		return custumer.getPaging().getTotal();
	}

	@Override
	public int pruebaResponseCode(String idCustomer) {
		
		return mercadoPago.cardsAcces(idCustomer);
	}

}