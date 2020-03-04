package com.castellana.ecommerce.service;

import com.castellana.ecommerce.dto.pago.VentaDto;

public interface PruebaService {
	
	int insertaVenta(VentaDto venta);
	int usuarioMercadoPago(String mail);
	int pruebaResponseCode(String idCustomer);
}