package com.castellana.ecommerce.dto.pago;

public class PayerTA {

	private String first_name;
	private String last_name;
	private String email;

	private String type;
	private String id;

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PayerTA [first_name=" + first_name + ", last_name=" + last_name + ", email=" + email + ", type=" + type
				+ ", id=" + id + "]";
	}

}
