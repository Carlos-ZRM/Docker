package com.castellana.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.dto.ChangePasswordDto;
import com.castellana.ecommerce.models.ProductoCarritoModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.repository.ArticuloRepository;
import com.castellana.ecommerce.repository.AuthRepository;
import com.castellana.ecommerce.repository.UsuarioRepository;
import com.castellana.ecommerce.service.MailService;
import com.castellana.ecommerce.service.PasswordFactoryService;
import com.castellana.ecommerce.service.UsuarioService;
import com.castellana.ecommerce.dto.BotellasCalculoEventoDto;


@Service
public class UsuarioServiceImpl  implements UsuarioService{
	
	private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);
	private static final Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ArticuloRepository articuloRepository;
	
	@Autowired
	private AuthRepository authRep;
	
	@Autowired
	private PasswordFactoryService paswordFactoryService;

	@Autowired
	private MailService mailService;
	
	@Override
	public int registraUsuario(UsuarioModel usuario) {
		int idUsuario = 0;
		idUsuario = usuarioRepository.registraUsuario(usuario);
		if (idUsuario > 0 ) {
			boolean envioMail = mailService.sendMailNuevoUsuario(usuario.getNombre(), usuario.getEmail(), usuario.getPassword());
			if(envioMail) {
				log.info("SE ENVIO MAIL NUEVO USUARIO");
			} else {
				log.info("NO SE ENVIO MAIL");
			}
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public boolean verificaCorreo(String correo) {
		Integer idUsr = usuarioRepository.verificaCorreo(correo);
		if (!idUsr.equals(0)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public int ressetPassword(String correo) {
		UsuarioModel usr = usuarioRepository.datosUsuario(correo);
		int res = 0;
		try {
			if (!usr.equals(null)) {
				res = -1;
				String nombreUsr = usr.getNombre() + " " + usr.getAppaterno() + " " + usr.getApmaterno();
				String motivo = "ECOMMERCE LA CASTELLANA - RESTABLECER CONTRASE�A";
				
				String newPassword  = paswordFactoryService.generarNuevaPassword();
				ArrayList<String> to = new ArrayList<>();
//	            ArrayList<String> cc = new ArrayList<>();
	            to.add(usr.getEmail());
	            
	            try {
	            	if (mailService.sendMailRessetPassword(newPassword, correo, nombreUsr, motivo)) {
	            		int update = usuarioRepository.updatePasswordUsuario(usr.getIdUsuario(), newPassword);
	            		log.info("SE ACTUALIZO LA PSW: " + update);
	            	} else {
	            		log.error("NO SE ACTUALIZO LA CONTRASE�A");
	            	}
				} catch (Exception e) {
					log.error("CORREO NO ENVIADO");
				}
	        } else {
				res = 0;
			}
			return res;
		
		} catch (Exception e) {
			return res;
		}
	}

	@Override
	public int suscripcionNewslatter(String correo) {
		int usr = usuarioRepository.suscripcionActivaNewsletter(correo);
		int status = 0;
		logger.info("BANDERA SUSCRITO A NL: " + usr + " USUARIO: " + correo);
		try {
			if (usr == 0) {
				
				String motivo = "¡Gracias por registrate!";
				
				boolean envio = mailService.sendMailNewsletter(correo, motivo);
				
				if (envio) {
					status = usuarioRepository.insertarUsuarioNL(correo);
				} else {
					status = 2;
				}
				
			} else {
				status = -1;
			}

			return status;
			
		} catch (Exception e) {
			return -1;
		}
	}

	@Override
	public boolean correoContacto(String nombre, String mail, String mensaje) {
		try {
			String motivo = "Mensaje Contactanos!";
			
			boolean envio = mailService.sendMailContacto(nombre, mail, mensaje, motivo);
			
			return envio;
		} catch (Exception e) {
			return false;
		}
	}
	
	@Override
	public List<Integer> insertarArticuloCarrito(int idToken, int idArticulo, int cantidad) {
		List<Integer> cantidades = new ArrayList<Integer>();
		
		int inventario = articuloRepository.validaInventario(idArticulo);
		cantidades.add(inventario);
		int cantidadEnCarrito = articuloRepository.cantidadArticuloCarrito(idToken, idArticulo);
		cantidades.add(cantidadEnCarrito);
		int insert = cantidad;
		
		List<ProductoCarritoModel> pcm = usuarioRepository.existeEnCarrito(idToken, idArticulo);
		int id = 0;
		int total = cantidad;
		
		if(pcm.size() != 0) {
			id = pcm.get(0).getId();
			total = pcm.get(0).getCantidad() + cantidad;
			if(inventario < cantidad) {
				insert = usuarioRepository.actualizarCantidadArticuloCarrito(pcm.get(0).getId(), inventario);
			} else {
				if (total > inventario) {
					insert = usuarioRepository.actualizarCantidadArticuloCarrito(pcm.get(0).getId(), inventario);
				} else {
					insert = usuarioRepository.actualizarCantidadArticuloCarrito(pcm.get(0).getId(), total);
				}
			}
		} else {
			logger.info("VA A AGREGAR " + cantidad + " UNIDAD(ES), DEL PRODUCTO CON ID: " + idArticulo + " AL CARRITO.");
			insert = usuarioRepository.insertarArticuloCarrito(idToken, idArticulo, cantidad);
		}
		cantidades.add(total);
		cantidades.add(insert);
		
		log.info("array de cantidades: " + cantidades);
		return cantidades;
	}

	@Override
	public boolean eliminaArticuloCarrito(int idToken, int idVenta) {
		
		int elimina = usuarioRepository.deleteArticuloCarrito(idToken, idVenta);
		
		if (elimina != 0 ) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public List<Integer> actualizarCantidadArticuloCarrito(int idToken, int idVenta, int cantidad, int idArticulo) {
		List<Integer> cantidades = new ArrayList<Integer>();
		
		int inventario = articuloRepository.validaInventario(idArticulo);
		cantidades.add(inventario);
		
		int cambio = 0;
		try {
			if (cantidad == 0) {
				cambio = usuarioRepository.deleteArticuloCarrito(idToken, idVenta);
			} else {
				if (inventario > cantidad) {
					cambio = usuarioRepository.actualizarCantidadArticuloCarrito(idVenta, cantidad);
				} else {
					cambio = usuarioRepository.actualizarCantidadArticuloCarrito(idVenta, inventario);
				}
			}
			cantidades.add(cambio);
			return cantidades;
		}catch (Exception e) {
			return cantidades;
		}
	}

	@Override
	public List<ProductoModel> getArticulosCarrito(int token) {
		List<ProductoModel> productos = usuarioRepository.getArticulosCarrito(token);
		return productos;
	}

	@Override
	public int cambioPassword(ChangePasswordDto update) {
		
		int idUsuario = usuarioRepository.verificaCorreo(update.getUsuario());
		
		if (authRep.oldpwdCorrecta(update.getUsuario(), update.getOldpwd())) {
			return usuarioRepository.updatePasswordUsuario(idUsuario, update.getNewpwd());
		} else {
			log.info("Contrase�a incorrecta");
			return 0;
		}
	}

	@Override
	public BotellasCalculoEventoDto calculaEvento(int personas){
		BotellasCalculoEventoDto bce = new BotellasCalculoEventoDto();

		switch (personas) {
			case 50:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 80:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 100:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 150:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 200:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 250:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 300:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 350:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 400:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 450:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 500:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 550:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 600:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 700:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			case 800:
				bce = usuarioRepository.getBotellasCalculoEvento(personas);
				break;
			default:
				bce = usuarioRepository.getBotellasCalculoEvento(100);
				break;
		}
		return bce;
	}
}
