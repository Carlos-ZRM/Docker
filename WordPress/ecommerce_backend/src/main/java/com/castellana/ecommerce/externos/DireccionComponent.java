package com.castellana.ecommerce.externos;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.castellana.ecommerce.dto.DireccionDTO;
import com.castellana.ecommerce.dto.DireccionDto;
import com.castellana.ecommerce.dto.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DireccionComponent {

	private static final Logger log = LoggerFactory.getLogger(DireccionComponent.class);

	public Response callApiSepomex(String cp) {
		log.info("ENTRA A LA API DE SEPOMEX");
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
		// Request to return JSON format
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json");
		headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
		// HttpEntity<String>: To get result as String.
		HttpEntity<DireccionDTO> entity = new HttpEntity<DireccionDTO>(headers);
		ResponseEntity<DireccionDTO> response = restTemplate.exchange(
				String.format("https://api-sepomex.hckdrk.mx/query/info_cp/%s?type=simplified",cp),
				HttpMethod.GET, entity, DireccionDTO.class);
		
		DireccionDTO direccion = response.getBody();
				
		return direccion.getResponse();
		
	}

	public String callApiHerokuApp(String cp) {
		String responseWS = "";
		try {
			log.info("ENTRA A LA API DE HEROKUAPP");
			RestTemplate restTemplate = new RestTemplate();

			ObjectMapper objectMapper = new ObjectMapper();

			HttpHeaders headers = new HttpHeaders();
//			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
			// Request to return JSON format
//			headers.setContentType(MediaType.APPLICATION_JSON);
			// HttpEntity<String>: To get result as String.
			HttpEntity<DireccionDto> entity = new HttpEntity<DireccionDto>(headers);

			ResponseEntity<DireccionDto> response = restTemplate.exchange(
//					"https://api-codigos-postales.herokuapp.com/v2/codigo_postal/" + cp, //
					"https://api-sepomex.hckdrk.mx/query/info_cp/" + cp,
					HttpMethod.GET, entity, DireccionDto.class);

			responseWS = objectMapper.writeValueAsString(response.getBody());
			
			log.info("Response HerokuApp" + objectMapper.writeValueAsString(response.getBody()));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return responseWS;
	}

}
