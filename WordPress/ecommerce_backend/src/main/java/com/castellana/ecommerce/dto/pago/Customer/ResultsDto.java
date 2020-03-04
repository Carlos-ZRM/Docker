package com.castellana.ecommerce.dto.pago.Customer;

public class ResultsDto {

	private String id;
	private String email;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "ResultsDto [id=" + id + ", email=" + email + "]";
	}

}