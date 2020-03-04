package com.castellana.ecommerce.dto.pago;

public class ShipmentsDto {

	private AddressDto receiver_address;

	public AddressDto getReceiver_address() {
		return receiver_address;
	}

	public void setReceiver_address(AddressDto receiver_address) {
		this.receiver_address = receiver_address;
	}

	@Override
	public String toString() {
		return "ShipmentsDto [receiver_address=" + receiver_address + "]";
	}

}
