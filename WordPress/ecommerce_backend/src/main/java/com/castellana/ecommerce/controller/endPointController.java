package com.castellana.ecommerce.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prueba")
public class endPointController {
	
	private static final Logger LOG = LoggerFactory.getLogger(endPointController.class);
	
	@GetMapping("/200/")
	String prueba() {
		LOG.info("ENTRA EN EL END POINT");
		return "EXITOSA: STATUS 200";
	}

}
