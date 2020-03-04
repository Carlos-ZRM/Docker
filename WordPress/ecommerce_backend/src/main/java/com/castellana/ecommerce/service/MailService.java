package com.castellana.ecommerce.service;

import java.math.BigDecimal;
import java.util.List;

import javax.mail.MessagingException;

import com.castellana.ecommerce.dto.pago.PreferenciaPagoDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.dto.InfoMailDto;


public interface MailService {
	
	public boolean sendMailRessetPassword(String texto, String correo, String titulo, String motivo) throws MessagingException;
	public boolean sendMailNewsletter(String correo, String motivo);
	public boolean sendMailContacto(String nombre, String mail, String mensaje, String motivo);
	public boolean sendMailNuevoUsuario(String nombre, String mail, String password);
	//public boolean sendMailConfirmacionCompra(List<ProductoModel> productos, VentaDto venta, PreferenciaPagoDto pref, 
	//		DireccionUsuarioModel direccion, String fecha, int noBotellas, BigDecimal pvp, String email);
	public boolean sendMailConfirmacionCompra(InfoMailDto mail);
}
