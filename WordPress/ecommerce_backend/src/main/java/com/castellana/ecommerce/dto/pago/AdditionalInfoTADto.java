package com.castellana.ecommerce.dto.pago;

public class AdditionalInfoTADto {

	private PayerDto payer;
	private ShipmentsDto shipments;

	public PayerDto getPayer() {
		return payer;
	}

	public void setPayer(PayerDto payer) {
		this.payer = payer;
	}

	public ShipmentsDto getShipments() {
		return shipments;
	}

	public void setShipments(ShipmentsDto shipments) {
		this.shipments = shipments;
	}

	@Override
	public String toString() {
		return "AdditionalInfoTADto [payer=" + payer + ", shipments=" + shipments + "]";
	}

}
