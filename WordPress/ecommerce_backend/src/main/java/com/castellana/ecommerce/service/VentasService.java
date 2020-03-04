package com.castellana.ecommerce.service;

import java.util.List;

import com.castellana.ecommerce.dto.DetalleVentaDto;
import com.castellana.ecommerce.dto.pago.CardDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoTarjetaAlmacenadaDto;
import com.castellana.ecommerce.dto.pago.RespuestaPagoDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.VentaLDto;

public interface VentasService {
	
	List<Object> consultarCostoEnvio(String cpDestino, int idToken);
	RespuestaPagoDto MPCheckoutPersonalizado(CuerpoPagoDto pago);
	List<CardDto> getTarjetasAlmacenadas(String mail);
	List<Integer> getInstallmentsTarjetasAlmacenadas(String firstSix);
	RespuestaPagoDto MPTarjetaAlmacenada(CuerpoPagoTarjetaAlmacenadaDto pago);
	List<VentaLDto> obtienePedidos(int idCliente);
	List<DetalleVentaDto> getDetalleVenta(int idVenta);
}
