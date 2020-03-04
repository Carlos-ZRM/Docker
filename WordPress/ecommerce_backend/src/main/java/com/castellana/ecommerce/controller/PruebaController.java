package com.castellana.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.service.PruebaService;

@RestController
@RequestMapping("api/prueba")
public class PruebaController {
	
	@Autowired
	PruebaService pruebaService;
	
	@PostMapping("/inserta")
	int insertaVenta(@RequestBody VentaDto venta) {
		return pruebaService.insertaVenta(venta);
	}
	
	@GetMapping("/respuesta")
	int usuarioMercadoPago(String mail) {
		return pruebaService.usuarioMercadoPago(mail);
	}
	
	@GetMapping("/responseCode")
	int pruebaResponseCode(String idCustomer) {
		return pruebaService.pruebaResponseCode(idCustomer);
	}

}