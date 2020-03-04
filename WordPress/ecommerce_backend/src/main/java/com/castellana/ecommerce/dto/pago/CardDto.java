package com.castellana.ecommerce.dto.pago;

public class CardDto {

	private String id;
	private String first_six_digits;
	private String last_four_digits;
	private String customer_id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirst_six_digits() {
		return first_six_digits;
	}

	public void setFirst_six_digits(String first_six_digits) {
		this.first_six_digits = first_six_digits;
	}

	public String getLast_four_digits() {
		return last_four_digits;
	}

	public void setLast_four_digits(String last_four_digits) {
		this.last_four_digits = last_four_digits;
	}

	public String getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(String customer_id) {
		this.customer_id = customer_id;
	}

	@Override
	public String toString() {
		return "CardDto [id=" + id + ", first_six_digits=" + first_six_digits + ", last_four_digits=" + last_four_digits
				+ ", customer_id=" + customer_id + "]";
	}

}
