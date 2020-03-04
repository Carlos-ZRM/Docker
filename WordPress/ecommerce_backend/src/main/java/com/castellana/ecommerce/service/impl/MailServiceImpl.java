package com.castellana.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.castellana.ecommerce.configuration.MailConfiguration;
import com.castellana.ecommerce.dto.pago.PreferenciaPagoDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.service.MailService;
import com.castellana.ecommerce.dto.InfoMailDto;

@Service
public class MailServiceImpl implements MailService{

	private static final Logger LOG = LoggerFactory.getLogger(MailServiceImpl.class);
	
	@Autowired
	private MailConfiguration email;
	
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	@Autowired
	private Environment env;
	
	private JavaMailSender javaMailSender;
	@Autowired
	public MailServiceImpl(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Value("${spring.mail.username}")
	String username;

	@Override
	public boolean sendMailRessetPassword(String password, String correo, String usuario, String motivo) throws MessagingException {
		try {
			LOG.info(password);
			LOG.info(correo);
			LOG.info(usuario);
			LOG.info(motivo);
			LOG.info(username);
			
			//PRODUCCION
			Context ctx = new Context();
			ctx.setVariable("correo", correo);
			ctx.setVariable("password", password);
		    
		    
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        String htmlContent = templateEngine.process("correos/bodymailpwrdresset", ctx);
	        
	        helper.setTo(correo);
	        helper.setSubject("ECOMMERCE LA CASTELLANA - RESTABLECER CONTRASEÑA");
	        helper.setFrom(username);
	        
	        
	        
	        helper.setText(htmlContent,true);
	        javaMailSender.send(message);
		    
	        
	        //QA DESARROLLO
//			Context ctx = new Context();
//			ctx.setVariable("correo", correo);
//			ctx.setVariable("password", password);
//			MimeMessage mimeMessage = email.mailSender().createMimeMessage();
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			message.setSubject(motivo);
//			message.setTo(correo);
//			
//			String htmlContent = templateEngine.process("correos/bodymailpwrdresset", ctx);
//			message.setText(htmlContent, true);
//			
//			email.mailSender().send(mimeMessage);
			
			return true;
		}catch (Exception e) {
			return false;
		}
	}
	
	

	@Override
	public boolean sendMailNewsletter(String correo, String motivo) {
		try {
			//PRODUCCION
			Context ctx = new Context();
		    
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        String htmlContent = templateEngine.process("correos/bodymailnewsletter", ctx);
	        
	        helper.setTo(correo);
	        helper.setSubject(motivo);
	        helper.setFrom(username);
	        
	        helper.setText(htmlContent,true);
	        javaMailSender.send(message);
			
			
//			QA Y PRUEBAS
//			Context ctx = new Context();
//			
//			MimeMessage mimeMessage = email.mailSender().createMimeMessage();
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			message.setSubject(motivo);
//			message.setTo(correo);
//			
//			String htmlContent = templateEngine.process("correos/bodymailnewsletter", ctx);
//			message.setText(htmlContent, true);
//			
//			email.mailSender().send(mimeMessage);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendMailContacto(String nombre, String mail, String mensaje, String motivo) {
		try {
			
			
			//PRODUCCION
			Context ctx = new Context();
			ctx.setVariable("nombre", nombre);
			ctx.setVariable("email", mail);
			ctx.setVariable("mensaje", mensaje);
		    
		    
		    MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        String htmlContent = templateEngine.process("correos/bodymailcontacto", ctx);
	        
	        helper.setTo("info@lacastellana.com");
//			helper.setTo("cfhagg@gmail.com");
	        helper.setSubject(motivo);
	        helper.setFrom(username);
	        
	        
	        
	        helper.setText(htmlContent,true);
	        javaMailSender.send(message);
	        
	        
	        
			//PRUEBAS Y QA
//			Context ctx = new Context();
//			ctx.setVariable("nombre", nombre);
//			ctx.setVariable("email", mail);
//			ctx.setVariable("mensaje", mensaje);
//			
//			MimeMessage mimeMessage = email.mailSender().createMimeMessage();
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			message.setSubject(motivo);
//			message.setTo("ixchel.castillo@wundertec.com");
//			
//			String htmlContent = templateEngine.process("correos/bodymailcontacto", ctx);
//			message.setText(htmlContent, true); 
//			
//			email.mailSender().send(mimeMessage);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendMailNuevoUsuario(String nombre, String mail, String password) {
		try {
			Context ctx = new Context();
			ctx.setVariable("nombre", nombre);
			ctx.setVariable("email", mail);
			ctx.setVariable("password", password);
			
			
			//PRODUCCION
			MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        String htmlContent = templateEngine.process("correos/bodyMailNewUser", ctx);
	        
	        helper.setTo(mail);
	        helper.setSubject("Registro Exitoso");
	        helper.setFrom(username);
	        helper.setText(htmlContent,true);
	        
	        javaMailSender.send(message);
			
			
			
			//DESARROLLO Y QA
//			MimeMessage mimeMessage = email.mailSender().createMimeMessage();
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			message.setSubject("Registro Exitoso");
//			message.setTo(mail);
//			
//			String htmlContent = templateEngine.process("correos/bodyMailNewUser", ctx);
//			message.setText(htmlContent, true); 
//			
//			email.mailSender().send(mimeMessage);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean sendMailConfirmacionCompra(InfoMailDto mail) {
		try {
			Context ctx = new Context();
			LOG.info("------------------------------------------------------------------123---------------");
			LOG.info(mail.toString());
			LOG.info(mail.getProductos().get(0).getImagen1());
			ctx.setVariable("pro", mail.getProductos());
			ctx.setVariable("ven", mail.getVenta());
			ctx.setVariable("pre", mail.getPref());
			ctx.setVariable("dir", mail.getDireccion());
			ctx.setVariable("date", mail.getFecha());
			ctx.setVariable("noPro", mail.getNoBotellas());
			ctx.setVariable("pvp", mail.getPvp());
			
			//PRODUCCION
			MimeMessage message = javaMailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        
	        String htmlContent = templateEngine.process("correos/bodyMailConfirmacionPagoCompra", ctx);
	        
	        helper.setTo(mail.getEmail());
	        helper.setSubject("¡Correo de compra!");
	        helper.setFrom(username);
	        helper.setText(htmlContent,true);
	        
	        javaMailSender.send(message);
			
			
			//DESARROLLO Y QA
//			MimeMessage mimeMessage = email.mailSender().createMimeMessage();
//			MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//			
//			message.setSubject("¡Correo de compra!");
//			message.setTo(mail.getEmail());
//			LOG.info(mail.getEmail());
//			String htmlContent = templateEngine.process("correos/bodyMailConfirmacionPagoCompra", ctx);
//			message.setText(htmlContent, true); 
//			
//			email.mailSender().send(mimeMessage);
			
			return true;
		} catch (Exception e) {
			LOG.info("ERROR MAIL CONFIRMACION COMPRA");
			return false;
		}
		
	}
}

