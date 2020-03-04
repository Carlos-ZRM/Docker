package com.castellana.ecommerce.dto.pago;

//import net.minidev.json.JSONObject;

public class RespuestaPagoDto {

	private boolean operation;
	private String id;
	private String status;
	private String status_detail;
	private String paymentObject;

	public boolean isOperation() {
		return operation;
	}

	public void setOperation(boolean operation) {
		this.operation = operation;
	}

	public String gettId() {
		return id;
	}

	public void setId(String paymentId) {
		this.id = paymentId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String statusResponse) {
		this.status = statusResponse;
	}

	public String getStatus_detail() {
		return status_detail;
	}

	public void setStatus_detail(String status_detail) {
		this.status_detail = status_detail;
	}

	public String getPaymentObject() {
		return paymentObject;
	}

	public void setPaymentObject(String paymentObject) {
		this.paymentObject = paymentObject;
	}

	@Override
	public String toString() {
		return "RespuestaPagoDto [operation=" + operation + ", paymentId=" + id + ", statusResponse="
				+ status + ", status_detail=" + status_detail + ", paymentObject=" + paymentObject + "]";
	}

}