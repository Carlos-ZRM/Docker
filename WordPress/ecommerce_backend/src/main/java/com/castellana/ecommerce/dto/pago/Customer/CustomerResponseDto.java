package com.castellana.ecommerce.dto.pago.Customer;

public class CustomerResponseDto {

	private PagingDto paging;
	private ResultsDto result;

	public PagingDto getPaging() {
		return paging;
	}

	public void setPaging(PagingDto paging) {
		this.paging = paging;
	}

	public ResultsDto getResult() {
		return result;
	}

	public void setResult(ResultsDto result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "CustomerResponseDto [paging=" + paging + ", result=" + result + "]";
	}

}