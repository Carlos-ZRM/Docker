package com.castellana.ecommerce.dto.pago;

public class PhoneDto {
	private String area_code;
	private String number;
	
	public PhoneDto(String area_code, String number) {
        this.area_code = area_code;
        this.number = number;
    }

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "PhoneDto [area_code=" + area_code + ", number=" + number + "]";
	}

}