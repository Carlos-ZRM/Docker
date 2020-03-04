package com.castellana.ecommerce.dto.pago;

import java.math.BigDecimal;

public class ItemDto {

	private String title;
	private BigDecimal unit_price;
	private int quantity;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getUnit_price() {
		return unit_price;
	}

	public void setUnit_price(BigDecimal unit_price) {
		this.unit_price = unit_price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "ItemDto [title=" + title + ", unit_price=" + unit_price + ", quantity=" + quantity + "]";
	}

}
