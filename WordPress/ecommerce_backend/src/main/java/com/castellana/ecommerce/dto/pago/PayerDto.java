package com.castellana.ecommerce.dto.pago;

public class PayerDto {

	private String first_name;
	private String last_name;
//	private String email;
//
//	private String type;
//	private String id;

	private PhoneDto phone;
	private AddressDto address;

	private String registration_date;

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

//	public String getEmail() {
//		return email;
//	}
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}

	public PhoneDto getPhone() {
		return phone;
	}

	public void setPhone(PhoneDto phone) {
		this.phone = phone;
	}

	public AddressDto getAddress() {
		return address;
	}

	public void setAddress(AddressDto address) {
		this.address = address;
	}

	public String getRegistration_date() {
		return registration_date;
	}

	public void setRegistration_date(String registration_date) {
		this.registration_date = registration_date;
	}

	@Override
	public String toString() {
		return "PayerDto [first_name=" + first_name + ", last_name=" + last_name + /*", email=" + email + ", type=" + type
				+ ", id=" + id +*/ ", phone=" + phone + ", address=" + address + ", registration_date="
				+ registration_date + "]";
	}

}