package com.castellana.ecommerce.repository;

import java.math.BigDecimal;
import java.util.List;

import com.castellana.ecommerce.dto.CostosDto;
import com.castellana.ecommerce.dto.DetalleVentaDto;
import com.castellana.ecommerce.dto.UsuarioDto;
import com.castellana.ecommerce.dto.pago.PaqueteEstafetaDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.VentaLDto;
import com.castellana.ecommerce.models.ProductoModel;

public interface VentasRepository {
	
	int getBotellasCarrito(int idToken);
	int getBotellasCarritoCalculoEnvio(int idToken);
	int getZona(String cpDestino);
	CostosDto getCostosEnvio(String codigoPostal, int zona, int numBotellas);
	CostosDto getCostosEnvio12mas(String codigoPostal, int zona, int numBotellas);
	String getPrecioEnvioGratis();
	PaqueteEstafetaDto getPaqueteEstafeta(int numBotellas);
	int getIdTipoEnvioEstafeta(String tipoServicio);
	UsuarioDto getUsuarioPorIdDireccion(int idDireccion);
	int registrarVenta(VentaDto venta);
	int registrarVentaArticulo(ProductoModel p, int idVenta);
	List<Integer> getIdVentasExpontaneas(int idToken);
	List<VentaLDto> getComprasCliente(int idCliente);
	List<DetalleVentaDto> getDetalleVenta(int idVenta);
	BigDecimal getPeso(int token);
	CostosDto getCostoForZona(int zona);
}