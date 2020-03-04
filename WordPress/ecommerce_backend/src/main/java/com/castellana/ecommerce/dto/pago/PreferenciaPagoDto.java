package com.castellana.ecommerce.dto.pago;

import java.math.BigDecimal;

public class PreferenciaPagoDto {

	private Payer_dto payer;
	private BigDecimal transaction_amount;
	private String payment_method_id;
	private String token;
	private int installments;
	private int issuer_id;
	private AdditionalInfoDto additional_info;

	public Payer_dto getPayer() {
		return payer;
	}

	public void setPayer(Payer_dto payer) {
		this.payer = payer;
	}

	public BigDecimal getTransaction_amount() {
		return transaction_amount;
	}

	public void setTransaction_amount(BigDecimal transaction_amount) {
		this.transaction_amount = transaction_amount;
	}

	public String getPayment_method_id() {
		return payment_method_id;
	}

	public void setPayment_method_id(String payment_method_id) {
		this.payment_method_id = payment_method_id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getInstallments() {
		return installments;
	}

	public void setInstallments(int installments) {
		this.installments = installments;
	}

	public int getIssuer_id() {
		return issuer_id;
	}

	public void setIssuer_id(int issuer_id) {
		this.issuer_id = issuer_id;
	}

	public AdditionalInfoDto getAdditional_info() {
		return additional_info;
	}

	public void setAdditional_info(AdditionalInfoDto additional_info) {
		this.additional_info = additional_info;
	}

	@Override
	public String toString() {
		return "PreferenciaPagoDto [payer=" + payer + ", transaction_amount=" + transaction_amount
				+ ", payment_method_id=" + payment_method_id + ", token=" + token + ", installments=" + installments
				+ ", issuer_id=" + issuer_id + ", additional_info=" + additional_info + "]";
	}

}