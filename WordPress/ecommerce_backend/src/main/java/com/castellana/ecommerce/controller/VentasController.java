package com.castellana.ecommerce.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.pago.CuerpoPagoDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoTarjetaAlmacenadaDto;
import com.castellana.ecommerce.dto.pago.RespuestaPagoDto;
import com.castellana.ecommerce.security.JwtUtil;
import com.castellana.ecommerce.service.VentasService;

@RestController
@RequestMapping("api/ventas")
public class VentasController {
	
	@Autowired
	VentasService ventasSer;
	 
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(VentasController.class);
	
	@GetMapping("/tipoenvio/")
	List<Object> consultarCostoEnvio(@RequestHeader("Authorization") String token, String cpDestino, int idToken ) throws AuthenticationException{
		jwtUtil.validateToken(token);
		LOG.info("IdToken: " + idToken + " ---------------  Codigo Postal: " + cpDestino );
		List<Object> respuesta = ventasSer.consultarCostoEnvio(cpDestino, idToken);
		LOG.info("RESPUESTA DE COSTO DE ENVIO: " + respuesta);
		return respuesta;
	}
	
	@PostMapping("/pagos/nueva/")
	RespuestaPagoDto MPCheckoutPersonalizado(@RequestHeader("Authorization") String token, @RequestBody CuerpoPagoDto pago) throws AuthenticationException {
		jwtUtil.validateToken(token);
		LOG.info("Entra al controller: " + pago.toString());
		return ventasSer.MPCheckoutPersonalizado(pago);
	}
	
	@GetMapping("/pagos/installments/")
	List<Integer> tarjetasClienteInstallments(@RequestHeader("Authorization") String token,
			String firstSix) throws AuthenticationException{
		jwtUtil.validateToken(token);
		LOG.info("FIRST SIX NUM: " + firstSix);
		List<Integer> installments = ventasSer.getInstallmentsTarjetasAlmacenadas(firstSix);
		LOG.info("INSTALLMENTS: " + installments.toString());
		return installments;
	}
	
	@PostMapping("/pagos/registrada/")
	RespuestaPagoDto MPTarjetaPago(@RequestHeader("Authorization") String token, 
			@RequestBody CuerpoPagoTarjetaAlmacenadaDto pago) throws AuthenticationException {
		jwtUtil.validateToken(token);
		RespuestaPagoDto pagoTarjetaAlmacenada = ventasSer.MPTarjetaAlmacenada(pago);
		return pagoTarjetaAlmacenada;
	}
	
		
}
