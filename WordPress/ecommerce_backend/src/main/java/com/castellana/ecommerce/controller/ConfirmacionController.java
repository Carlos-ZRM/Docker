package com.castellana.ecommerce.controller;

import java.util.List;

import javax.naming.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.security.JwtUtil;
import com.castellana.ecommerce.service.ArticuloService;
import com.castellana.ecommerce.service.DireccionUsuarioService;

@RestController
@RequestMapping("api/confirmacion")
public class ConfirmacionController {
	
	@Autowired
	private ArticuloService articuloSer;
	
	@Autowired
	private DireccionUsuarioService direccionUsrSer;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private static final Logger LOG = LoggerFactory.getLogger(ConfirmacionController.class);
	
	@GetMapping("/verificainventario")
	List<Object> productosConInventario(@RequestHeader("Authorization") String token, int idToken) throws AuthenticationException{
		jwtUtil.validateToken(token);
		
		List<Object> lista = (List<Object>) articuloSer.verificaExistenciaProductos(idToken);
		
		LOG.info("PRODUCTOS CON INVENTARIO: " + lista.get(0));
		LOG.info("LOS PRODUCTOS FUERON MODIFICADOS O ELIMINADOS POR FALTA DE INVENTARIO: " + lista.get(1));
		
		return lista;
	}
	
	@GetMapping("/verificadireccion")
	boolean existenciaDireccion(@RequestHeader("Authorization") String token, int idUsuario) throws AuthenticationException {
		jwtUtil.validateToken(token);
		
		boolean direcciones = direccionUsrSer.existenciaDireccion(idUsuario);
		
		LOG.info("EXISTEN DIRECCIONES: " + direcciones);
		
		return direcciones;
	}

}
