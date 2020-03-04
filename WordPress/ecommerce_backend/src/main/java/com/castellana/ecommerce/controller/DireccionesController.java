package com.castellana.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.Response;
import com.castellana.ecommerce.externos.DireccionComponent;
import com.castellana.ecommerce.service.DireccionUsuarioService;

@RestController
@RequestMapping("api/direcciones")
public class DireccionesController {

	private static final Logger log = LoggerFactory.getLogger(DireccionesController.class);

	@Autowired
	DireccionComponent direccionC;
	
	@Autowired
	DireccionUsuarioService dirUs;

	/*
	@GetMapping("/codigopostal/")
	String colonias(String cp) {
		String colonias = "";
		try {
			// colonias = direccionC.callApiSepomex(cp);
			colonias = direccionC.callApiHerokuApp(cp);
			log.debug("RESPUESTA DEL COMPONENT s: " + colonias);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return colonias;
	}
	*/
	
	@GetMapping("/codigopostal/")
	Response colonias(String cp) {
		Response colonias = null;
		try {
			colonias = direccionC.callApiSepomex(cp);
			log.debug("RESPUESTA DEL COMPONENT s: " + colonias);
		} catch (Exception e) {
			log.error("Error", e);
		}
		return colonias;
	}
	
	@GetMapping("/validaCP/")
	boolean validaCP(String cp) {
		boolean val = dirUs.validaCP(cp);
		return val;
	}

}
