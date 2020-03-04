package com.castellana.ecommerce.externos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.castellana.ecommerce.dto.UsuarioDto;
import com.castellana.ecommerce.dto.pago.CardDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoDto;
import com.castellana.ecommerce.dto.pago.PaqueteEstafetaDto;
import com.castellana.ecommerce.dto.pago.PreferenciaPagoDto;
import com.castellana.ecommerce.dto.pago.PreferenciaPagoTADto;
import com.castellana.ecommerce.dto.pago.RespuestaPagoDto;
import com.castellana.ecommerce.dto.pago.Customer.CustomerResponseDto;
import com.castellana.ecommerce.dto.pago.Customer.PagingDto;
import com.castellana.ecommerce.dto.pago.Customer.ResultsDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MercadoPagoComponent {

	@Value("${mercadoLibre.mp.paymentURL}")
	private String paymentURL;
	
	@Value("${mercadoLibre.acces_token}")
	private String accessToken;
	
	@Value("${mercadoLibre.mp.public_key}")
	private String publicKey;
	
	@Value("${mercadoLibre.mp.timeout}")
	private int timeout;
	
	
	private static final Logger LOG = LoggerFactory.getLogger(MercadoPagoComponent.class);
	
	
	public RespuestaPagoDto procesaPago(CuerpoPagoDto pago, PreferenciaPagoDto pref, UsuarioDto usr, PaqueteEstafetaDto paquete, 
			int servicioEnvio) {
		RespuestaPagoDto respuesta = new RespuestaPagoDto();
		
		try {

			String responseWS = "";
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(pref);
			    
		    RestTemplate restTemplate = new RestTemplate();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    
		    LOG.info("RES WS: " + responseWS);
		    
		    HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
		    LOG.info("URL: " + paymentURL+"access_token=" + accessToken);
		    ResponseEntity<String> response = restTemplate.exchange(paymentURL + "access_token=" + accessToken, HttpMethod.POST, entity, String.class);
		    
		    LOG.info("RESPONSE BODY: \n" + response.getBody() );
		    
		    JsonNode respuestajson = objectMapper.readTree(response.getBody());
		    
		    respuesta.setId(respuestajson.path("id").asText());
		    respuesta.setStatus(respuestajson.path("status").asText());
		    respuesta.setStatus_detail(respuestajson.path("status_detail").asText());
		    respuesta.setPaymentObject(response.getBody());
		    
		    if (response.getStatusCodeValue() == 201) {
		    	respuesta.setOperation(true);
		    } 
		    
		    LOG.info("HTTP code: " + response.getStatusCodeValue());
		    LOG.info("LO QUE REGRESA " + response.getBody());
		    LOG.info("LO QUE REGRESA OBJETO " + respuesta.toString());

			return respuesta;
		} catch (Exception e) {
			LOG.error("ERROR MERCADO PAGO : " + e);
			e.printStackTrace();
			return null;
		}
		
	}
	
	public CustomerResponseDto regresaStatusRegistradoUsuario(String email) {
		CustomerResponseDto custumer = new CustomerResponseDto();
		PagingDto paging = new PagingDto();
		ResultsDto results = new ResultsDto();
		try {
			String responseWS = "";
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(email);
			
			RestTemplate restTemplate = new RestTemplate();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    LOG.info("RES WS: " + responseWS);
		    
		    HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
		    ResponseEntity<String> response = restTemplate.exchange(
		    		"https://api.mercadopago.com/v1/customers/search?" + "access_token=" + accessToken +"&email="+ email, HttpMethod.GET, entity, String.class);
		    
		    JsonNode respuestajson = objectMapper.readTree(response.getBody());
		    LOG.info("RESPONSE BODY: " + response.getBody());
		    LOG.info("JSON BODY: " + respuestajson);
		    LOG.info("TOTAL: " + respuestajson.path("paging").path("total").asText());
		    LOG.info("TOTAL: " + respuestajson.path("results"));
			
			String total = respuestajson.path("paging").path("total").asText();

			if ( !total.equals("0") ) {
				results.setEmail(respuestajson.get("results").get(0).path("email").asText());
		    	results.setId(respuestajson.get("results").get(0).path("id").asText());
			}else{
				results.setEmail("0");
		    	results.setId("0");
			}

		    paging.setTotal(Integer.parseInt(respuestajson.path("paging").path("total").asText()));
		    paging.setOffset(Integer.parseInt(respuestajson.path("paging").path("offset").asText()));
		    paging.setLimit(Integer.parseInt(respuestajson.path("paging").path("limit").asText()));
		    
		    custumer.setPaging(paging);
		    custumer.setResult(results);
		    LOG.info("CUSTUMER dto: " + custumer);
			return custumer;
		} catch (Exception e) {
			LOG.info("ERROR: " + e);
			return custumer;
		}
	}
	
	public int cardsAcces(String idCustomer) {
		try {
			
			String responseWS = "";
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(idCustomer);
			
			RestTemplate restTemplate = new RestTemplate();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    LOG.info("RES WS: " + responseWS);
		    
		    HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
		    ResponseEntity<String> responseE = restTemplate.exchange(
		    		"https://api.mercadopago.com/v1/customers/" + idCustomer + "/cards?access_token=" + accessToken, 
		    		HttpMethod.GET, entity, String.class);
		    
		   return responseE.getStatusCodeValue();
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			return 0;
		}
	}
	
	public List<CardDto> obtieneTarjetasMP(String mail){
		List<CardDto> tarjetas = new ArrayList<CardDto>();
		CardDto cardDto;
		try {
			String responseWS = "";
			RestTemplate restTemplete = new RestTemplate();
			
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(mail);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
			ResponseEntity<String> responseE = restTemplete.exchange(
					"https://api.mercadopago.com/v1/customers/search?" + "access_token=" + accessToken + "&email=" + mail, 
					HttpMethod.GET, entity, String.class);
			
			LOG.info("URL: " + "https://api.mercadopago.com/v1/customers/search?" + "access_token=" + accessToken + "&email=" + mail);
			LOG.info("RESPUESTA: " + responseE);
			LOG.info("RESPUESTA status code: " + responseE.getStatusCodeValue());
			LOG.info("RESPUESTA body: " + responseE.getBody());
			
			JsonNode respuestajson = objectMapper.readTree(responseE.getBody());
			
			try {
				LOG.info("RESULTS: " + respuestajson.path("results").asText());
				LOG.info("No TARJETAS: " + respuestajson.path("results").get(0).path("cards").size());
				
				for(int i= 0; i < respuestajson.path("results").get(0).path("cards").size(); i++) {
					cardDto = new CardDto();
					LOG.info("TARJETA id: " + respuestajson.path("results").get(0).path("cards").get(i).path("id").asText());
					cardDto.setId(respuestajson.path("results").get(0).path("cards").get(i).path("id").asText());
					LOG.info("Primeros 6 dig: " + respuestajson.path("results").get(0).path("cards").get(i).path("first_six_digits").asText());
					cardDto.setFirst_six_digits(respuestajson.path("results").get(0).path("cards").get(i).path("first_six_digits").asText());
					LOG.info("Ultimos 4 dig: " + respuestajson.path("results").get(0).path("cards").get(i).path("last_four_digits").asText());
					cardDto.setLast_four_digits(respuestajson.path("results").get(0).path("cards").get(i).path("last_four_digits").asText());
					LOG.info("customer_id: " + respuestajson.path("results").get(0).path("cards").get(i).path("customer_id").asText());
					cardDto.setCustomer_id(respuestajson.path("results").get(0).path("cards").get(i).path("customer_id").asText());
					
					tarjetas.add(cardDto);
				}
			} catch (Exception e) {
				LOG.error("ERROR NO HAY TARJETAS GUARDADAS: " + e);
			}
			
			
		} catch (Exception e) {
			LOG.error("ERROR obtieneTarjetasMP: " + e);
			e.printStackTrace();
		}
		return tarjetas;
	}
	
	public List<Integer> getInstallments(String firstSix){
		List<Integer> installments = new ArrayList<Integer>();
		Integer installment;
		try {
			String responseWS = "";
			RestTemplate restTemplete = new RestTemplate();
			
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(firstSix);
			
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
			
			ResponseEntity<String> responseE = restTemplete.exchange(
					"https://api.mercadopago.com/v1/payment_methods/installments?public_key=" + publicKey + "&bin=" + firstSix, 
					HttpMethod.GET, entity, String.class);
			
			LOG.info("https://api.mercadopago.com/v1/payment_methods/installments?public_key=" + publicKey + "&bin=" + firstSix);
			
			JsonNode respuestajson = objectMapper.readTree(responseE.getBody());
			try {
				int noI = respuestajson.get(0).path("payer_costs").size();
				LOG.info("NO INST: " + noI);
				for (int i = 0; i<noI; i++) {
					installment = respuestajson.get(0).path("payer_costs").get(i).path("installments").asInt();
					installments.add(installment);
				}
				
			} catch (Exception e) {
				LOG.error("ERROR getInstallments: " + e);
			}
			
		} catch (Exception e) {
			LOG.error("ERROR getInstallments: " + e);
		}
		return installments;
	}
	
	public List<String> validaClienteYTarjeta(String idCliente, String idTarjeta) {	
		List<String> respuesta = new ArrayList<String>();
		try {

			String responseWS = "";
			ObjectMapper objectMapper = new ObjectMapper();
//			responseWS = objectMapper.writeValueAsString(idCliente);
//			
			LOG.info("CADENA: " + responseWS);
			    
		    RestTemplate restTemplate = new RestTemplate();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    
		    LOG.info("RES WS: " + responseWS);
		    
		    HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
		    LOG.info("URL: " + paymentURL+"access_token=" + accessToken);
		    ResponseEntity<String> response = restTemplate.exchange(
		    		"https://api.mercadopago.com/v1/customers/" + idCliente + "/cards/" + idTarjeta + "?access_token=" + accessToken,
		    			HttpMethod.GET, entity, String.class);
		    
		    LOG.info("https://api.mercadopago.com/v1/customers/" + idCliente + "/cards/" + idTarjeta + "?access_token=" + accessToken);
		    
		    LOG.info("RESPONSE BODY: \n" + response.getBody() );
		    
		    LOG.info("HTTP code: " + response.getStatusCodeValue());
		    
		    JsonNode respuestajson = objectMapper.readTree(response.getBody());
			
			LOG.info("------------------------------------");
			LOG.info("EL ID DE ISSUER: " +  respuestajson.path("issuer").path("id").asText());
			LOG.info("EL ID DE TARJETA: " +  respuestajson.path("payment_method").path("id").asText());
		    LOG.info("------------------------------------");
		    if (response.getStatusCodeValue() == 200) {
				//respuesta = Integer.parseInt(respuestajson.path("issuer").path("id").asText());
				respuesta.add(respuestajson.path("issuer").path("id").asText());
				respuesta.add(respuestajson.path("payment_method").path("id").asText());
		    } 
			
		} catch (Exception e) {
			LOG.error("ERROR VALIDACION CLIENTE Y TARJETA : " + e);
			e.printStackTrace();
		}
		return respuesta;
	}
	
	public RespuestaPagoDto validaInformacionAdicional(PreferenciaPagoTADto pref) {
		RespuestaPagoDto respuesta = new RespuestaPagoDto();
		
		try {
			
			String responseWS = "";
			ObjectMapper objectMapper = new ObjectMapper();
			responseWS = objectMapper.writeValueAsString(pref);
			    
		    RestTemplate restTemplate = new RestTemplate();
		    
		    HttpHeaders headers = new HttpHeaders();
		    headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    
		    LOG.info("RES WS: " + responseWS);
		    
		    HttpEntity<String> entity = new HttpEntity<String>(responseWS, headers);
		    ResponseEntity<String> response = restTemplate.exchange(
		    		paymentURL + "access_token=" + accessToken,
		    			HttpMethod.POST, entity, String.class);
		    LOG.info(paymentURL + "access_token=" + accessToken);
		    
		    LOG.info("Status code: " + response.getStatusCodeValue());
		    LOG.info("RESPONSE BODY: \n" + response.getBody() );
		    
		    JsonNode respuestajson = objectMapper.readTree(response.getBody());
		    
		    respuesta.setId(respuestajson.path("id").asText());
		    respuesta.setStatus(respuestajson.path("status").asText());
		    respuesta.setStatus_detail(respuestajson.path("status_detail").asText());
		    respuesta.setPaymentObject(response.getBody());
		    
		    if (response.getStatusCodeValue() == 201) {
		    	respuesta.setOperation(true);
		    } 
		    
		    LOG.info("HTTP code: " + response.getStatusCodeValue());
		    LOG.info("LO QUE REGRESA " + response.getBody());
		    LOG.info("LO QUE REGRESA OBJETO " + respuesta.toString());

			return respuesta;
		} catch (Exception e) {
			LOG.error("ERROR VALIDANDO INFORMACION ADICIONAL: " + e);
			e.printStackTrace();
			return null;
		}
	}
	
	
}