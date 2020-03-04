package com.castellana.ecommerce.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.dto.ChangePasswordDto;
import com.castellana.ecommerce.dto.DetalleVentaDto;
import com.castellana.ecommerce.dto.MensajeContactanos;
import com.castellana.ecommerce.dto.pago.CardDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.VentaLDto;
import com.castellana.ecommerce.dto.BotellasCalculoEventoDto;
import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.security.JwtUtil;
import com.castellana.ecommerce.service.DireccionUsuarioService;
import com.castellana.ecommerce.service.UsuarioService;
import com.castellana.ecommerce.service.VentasService;
import com.mysql.cj.log.Log;

@RestController
@RequestMapping("api/usuario")
public class UsuarioController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private DireccionUsuarioService direccionUsrService;
	
	@Autowired
	private VentasService ventasService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@ExceptionHandler( { AuthenticationException.class })
	void handlerAuthenticationException(AuthenticationException e, HttpServletResponse response) throws IOException {
		LOG.info("->" + HttpStatus.UNAUTHORIZED.value());
		response.sendError(HttpStatus.UNAUTHORIZED.value());
	}
	
	@PostMapping("/registro/")
	int registraUsuario(@RequestBody UsuarioModel usuario) {
		int idUsuario = usuarioService.registraUsuario(usuario);
		return idUsuario;
	}
	
	@GetMapping("/confirmacion/verificacorreo/")
	boolean verificaCorreo(@RequestParam(name="email") String correo) {
		boolean existe = usuarioService.verificaCorreo(correo);
		return existe;
	}
	
	@PostMapping("/auth/resetpassword/")
	boolean recuperaPassword(@RequestParam(name = "email") String email){
		
		int ressetPswrd = usuarioService.ressetPassword(email);
		
		if (ressetPswrd == -1) {
			return true;
		} else {
			return false;
		}
		
	}
	
	@PostMapping("/newsletter/")
	int correoNewsLeter(@RequestParam(name = "email") String email) {
		
		int mail = usuarioService.suscripcionNewslatter(email);
		
		return mail;
	}
	
	@PostMapping("/contacto/")
	boolean correoContacto(@RequestBody MensajeContactanos msj) {
		LOG.info("NOMBRE: " + msj.getNombre());
		LOG.info("MAIL: " + msj.getMail());
		LOG.info("MENSAJE: " + msj.getMensaje());
		boolean envio = usuarioService.correoContacto(msj.getNombre(), msj.getMail(), msj.getMensaje());
		
		return envio;
	}
	
	
	@PostMapping("/carrito/agrega/{token}")
	List<Integer> agregarArticuloCarrito(@PathVariable(name = "token") int idToken, int idArticulo, int cantidad) {
		
		List<Integer> cantidades = usuarioService.insertarArticuloCarrito(idToken, idArticulo, cantidad);
		LOG.info("ARRAY DE CANTIDADES: " + cantidades);
		return cantidades;
	}
	
	@PostMapping("/carrito/elimina/{token}")
	boolean eliminarArticuloCarrito(@PathVariable(name = "token") int idToken, int idVenta) {
		
		boolean elimina = usuarioService.eliminaArticuloCarrito(idToken, idVenta);
		
		return elimina;
	}
	
	@PostMapping("/carrito/update/{token}")
	List<Integer> actualizarArticuloCarrito(@PathVariable(name="token") int idToken, int idVenta, int cantidad, int idArticulo) {
		
		List<Integer> update = usuarioService.actualizarCantidadArticuloCarrito(idToken, idVenta, cantidad, idArticulo);
		
		return update;
	}
	
	@GetMapping("/carrito/getArticulos/{token}")
	List<ProductoModel> obtenerArticulosCarrito(@PathVariable(name="token") int token){
		List<ProductoModel> p = usuarioService.getArticulosCarrito(token);
		return p;
	}
	
	@PostMapping("/actualizapwd/")
	public int cambioPassword(@RequestHeader("Authorization") String token, @RequestBody ChangePasswordDto update) throws AuthenticationException {
		jwtUtil.validateToken(token);
		return usuarioService.cambioPassword(update);
	}
	
	@PostMapping("/{idCliente}/addDireccion/{token}")
	public boolean registraDireccion(@RequestHeader("Authorization") String token, 
			@RequestBody DireccionUsuarioModel direccion, @PathVariable int idCliente) throws AuthenticationException {
		LOG.info("NUEVA DIRECCION: " + direccion);
		boolean ins;
		jwtUtil.validateToken(token);
		ins = direccionUsrService.addDireccion(direccion);
		return ins;
	}
	
	@GetMapping("/direcciones/{idCliente}/")
	public List<DireccionUsuarioModel> obtenDirecciones(@RequestHeader("Authorization") String token, 
			@PathVariable(name="idCliente")  Integer idCliente) throws AuthenticationException {
		jwtUtil.validateToken(token);
		LOG.info("CONTROLLER SE OBTIENEN DIRECCION DEL USUARIO ID: " + idCliente);
		List<DireccionUsuarioModel> dir = direccionUsrService.getDirecciones(idCliente);
		return dir;
	}

	@PostMapping("/direcciones/{idCliente}/{idDireccion}")
	public boolean eliminarDireccion(@RequestHeader("Authorization") String token,
			@PathVariable(name="idCliente") int idCliente, @PathVariable(name="idDireccion") int idDireccion) throws AuthenticationException {
		jwtUtil.validateToken(token);
		return direccionUsrService.actualizarBorradoDireccion(idDireccion);
	}
	
	@GetMapping("/direcciones/detalle/{id}")
	public DireccionUsuarioModel detalleDireccion(@RequestHeader("Authorization") String token, 
			@PathVariable(name="id") int idDireccion) throws AuthenticationException {
		jwtUtil.validateToken(token);
		LOG.info("CONTROLLER CONSULTA EL DETALE DIRECCION ID: " + idDireccion);
		return direccionUsrService.getDetalleDireccion(idDireccion);
	}
	
	@PostMapping("/direcciones/update/{id}")
	public boolean actualizaDireccion(@RequestHeader("Authorization") String token,
			@PathVariable(name="id") int id, @RequestBody DireccionUsuarioModel direccion) throws AuthenticationException {
		jwtUtil.validateToken(token);
		LOG.info("ACTUALIZA DIRECCION ID: " + id);
		return direccionUsrService.updateDireccion(direccion);
	}
	
	@GetMapping("/{id}/tarjetas")
	List<CardDto> tarjetasCliente(@RequestHeader("Authorization") String token, 
			@PathVariable(name="id") int id, String mail) throws AuthenticationException{
		jwtUtil.validateToken(token);
		List<CardDto> tarjetas = ventasService.getTarjetasAlmacenadas(mail);
		return tarjetas;
	}
	
	@GetMapping("/{id}/ventas")
	List<VentaLDto> cargarMisPedidos(@RequestHeader("Authorization") String token, 
			@PathVariable(name="id") int idUser) throws AuthenticationException{
		jwtUtil.validateToken(token);
		List<VentaLDto> ventas = ventasService.obtienePedidos(idUser);
		return ventas;
	}
	
	@GetMapping("/detalleVenta/{id}")
	List<DetalleVentaDto> detalleVenta(@RequestHeader("Authorization") String token,
			@PathVariable(name="id") int id) throws AuthenticationException{
		jwtUtil.validateToken(token);
		List<DetalleVentaDto> detalle = ventasService.getDetalleVenta(id);
		return detalle;
	}

	@GetMapping("/calcularEvento/{numPers}")
	public BotellasCalculoEventoDto calcularEvento(@PathVariable(name="numPers")  Integer numPers) {
		LOG.info("CALULAR EVENTO CON NUMERO DE PERSONAS: " + numPers);
		BotellasCalculoEventoDto evento = usuarioService.calculaEvento(numPers);
		return evento;
	}

	
}
