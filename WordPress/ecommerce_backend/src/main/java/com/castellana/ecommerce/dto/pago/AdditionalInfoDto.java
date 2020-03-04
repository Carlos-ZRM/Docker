package com.castellana.ecommerce.dto.pago;

import java.util.ArrayList;

public class AdditionalInfoDto {

	private ArrayList<ItemDto> items;
	private PayerDto payer;
	private ShipmentsDto shipments;

	public ArrayList<ItemDto> getItems() {
		return items;
	}

	public void setItems(ArrayList<ItemDto> items) {
		this.items = items;
	}

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
		return "AdditionalInfoDto [items=" + items + ", payer=" + payer + ", shipments=" + shipments + "]";
	}

}
