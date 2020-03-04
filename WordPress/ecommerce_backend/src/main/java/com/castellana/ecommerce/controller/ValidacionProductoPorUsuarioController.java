package com.castellana.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.ParametrosValCompProdDTO;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.security.JwtUtil;
import com.castellana.ecommerce.service.ValidacionService;

@RestController
@RequestMapping("api/validacion")
public class ValidacionProductoPorUsuarioController {
	
	@Autowired
	ValidacionService valServ;

	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(ValidacionProductoPorUsuarioController.class);
	
	@GetMapping("/existente/")
//	List<Integer> validacionProducto(@RequestHeader("Authorization") String token, @RequestBody ParametrosValCompProdDTO parametros) throws AuthenticationException{
	List<ProductoModel> validacionProducto(@RequestHeader("Authorization") String token, int idUsuario, int idToken) throws AuthenticationException{
		jwtUtil.validateToken(token);
		ParametrosValCompProdDTO parametros = new ParametrosValCompProdDTO();
		LOG.info("------------------------------------------------>AQUI ENTRA A LA VALIDACION DE PRODUCTO ");
		
		parametros.setIdUsuario(idUsuario);
		parametros.setIdToken(idToken);
		List<Integer> lista = new ArrayList<Integer>();
		//PRUEBAS Y QA
		lista.add(0);
		lista.add(0);
		lista.add(0);
		//PRODUCCION
//		lista.add(2848);
//		lista.add(395);
//		lista.add(3910);
		
		parametros.setIdproductos(lista);
		parametros.setFecha("2019-12-08");
		
		List<ProductoModel> list = valServ.validacionProducto(parametros);
		LOG.info("Lista: " + list);
		return list;
	}
 	
	
}
