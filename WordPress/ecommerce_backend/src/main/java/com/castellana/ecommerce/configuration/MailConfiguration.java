package com.castellana.ecommerce.configuration;

import java.util.Collections;
import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Configuration
public class MailConfiguration implements ApplicationContextAware, EnvironmentAware{
	
	private static final String EMAIL_TEMPLETE_ENCODING = "EMAIL_TEMPLETE_ENCODING";
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private Environment env;

	public JavaMailSender mailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		log.info("EMAIL SENDER: " + mailSender.getUsername());
		mailSender.setHost(env.getProperty("spring.mail.host"));
		mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
		mailSender.setUsername(env.getProperty("spring.mail.username"));
		mailSender.setPassword(env.getProperty("spring.mail.password"));
		
		Properties pro = new Properties();
		
		// Gmail
		pro.put("mail.transport.protocol", "smtp");
		pro.put("mail.smtp.auth", "false");
		pro.put("mail.smtp.starttls.enable", "false");
		pro.put("mail.smtp.ssl.enable", "true");
		pro.put("mail.smtp.port", "465");
		pro.put("mail.smtp.socketFactory.port", "465");
		pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		//GMAIL
		mailSender.setJavaMailProperties(pro);
		
		// OUUTLOOK
//		pro.put("mail.transport.protocol", "smtp");
//		pro.put("mail.smtp.auth", "true");
//		pro.put("mail.smtp.starttls.enable", "true");
//		pro.put("mail.smtp.ssl.enable", "true");
//		pro.put("mail.smtp.port", "587");
//		pro.put("mail.smtp.socketFactory.port", "587");
//		pro.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//		pro.put("mail.tls", "true");
//		
//		//OUTLOOK
//		Transport transport;
//		Session session;
//		session = Session.getDefaultInstance(pro);
//		transport = session.getTransport("smtp");
		

		

		return mailSender;
	}
	
	@Bean 
	public TemplateEngine emailTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		// Resolver for TEXT emails
		templateEngine.addTemplateResolver(textTemplateResolver());
		// REsolver for HTML emails (except the editable one)
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		// Resolver for HTML editable emails (which will be treated as a String)
		templateEngine.addTemplateResolver(stringTemplateResolver());
		// Message source, internationalization specific to emails
		templateEngine.setTemplateEngineMessageSource(emailMessageSourse());
		
		return templateEngine;
	}
	
	@Bean
	public ResourceBundleMessageSource emailMessageSourse() {
		final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("mail/MailMessages");
		return messageSource;
	}
	
	private ITemplateResolver textTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("text/*"));
		templateResolver.setSuffix(".txt");
		templateResolver.setTemplateMode(TemplateMode.TEXT);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLETE_ENCODING);
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	
	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding(EMAIL_TEMPLETE_ENCODING);
		templateResolver.setCacheable(false);
		return templateResolver;
	}
	
	private ITemplateResolver stringTemplateResolver() {
		final StringTemplateResolver templateResolver = new StringTemplateResolver();

		templateResolver.setOrder(Integer.valueOf(3));
		templateResolver.setTemplateMode("HTML5");
		templateResolver.setCacheable(false);
		
		return templateResolver;
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		
	}
	
	
		
}
