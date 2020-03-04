package com.castellana.ecommerce.dto.pago;

public class AddressDto {

	private String zip_code;
	private String street_name;
	private String street_number;
	
    public AddressDto(String zip_code, String street_name, String street_number) {
        this.zip_code = zip_code;
        this.street_name = street_name;
        this.street_number = street_number;
    }

	public String getZip_code() {
		return zip_code;
	}

	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getStreet_number() {
		return street_number;
	}

	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}

	@Override
	public String toString() {
		return "AdressDto [zip_code=" + zip_code + ", street_name=" + street_name + ", street_number=" + street_number
				+ "]";
	}

}