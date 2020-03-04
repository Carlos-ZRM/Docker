package com.castellana.ecommerce.dto;

public class DireccionDTO {
	
	private String error;
	private String code_error;
	private String error_mesage;
	private Response response;
	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}
	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}
	/**
	 * @return the code_error
	 */
	public String getCode_error() {
		return code_error;
	}
	/**
	 * @param code_error the code_error to set
	 */
	public void setCode_error(String code_error) {
		this.code_error = code_error;
	}
	/**
	 * @return the error_mesage
	 */
	public String getError_mesage() {
		return error_mesage;
	}
	/**
	 * @param error_mesage the error_mesage to set
	 */
	public void setError_mesage(String error_mesage) {
		this.error_mesage = error_mesage;
	}
	/**
	 * @return the response
	 */
	public Response getResponse() {
		return response;
	}
	/**
	 * @param response the response to set
	 */
	public void setResponse(Response response) {
		this.response = response;
	}
	
}
