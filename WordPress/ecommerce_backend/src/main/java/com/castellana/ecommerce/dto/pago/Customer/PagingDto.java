package com.castellana.ecommerce.dto.pago.Customer;

public class PagingDto {

	private int offset;
	private int limit;
	private int total;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PagingDto [offset=" + offset + ", limit=" + limit + ", total=" + total + "]";
	}

}