package com.castellana.ecommerce.dto;

import java.math.BigDecimal;
import java.util.List;

import com.castellana.ecommerce.dto.pago.*;
import com.castellana.ecommerce.models.*;

public class InfoMailDto {

	private String email;
	private BigDecimal pvp;
	private int noBotellas;
	private String fecha;
	private DireccionUsuarioModel direccion;
	private PreferenciaPagoDto pref;
	private VentaDto venta;
	List<ProductoModel> productos;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getNoBotellas() {
		return noBotellas;
	}

	public void setNoBotellas(int noBotellas) {
		this.noBotellas = noBotellas;
	}


	public BigDecimal getPvp() {
		return pvp;
	}

	public void setPvp(BigDecimal pvp) {
		this.pvp = pvp;
	}

	public DireccionUsuarioModel getDireccion() {
		return direccion;
	}

	public void setDireccion(DireccionUsuarioModel direccion) {
		this.direccion = direccion;
	}

	public PreferenciaPagoDto getPref(){
		return pref;
	}

	public void setPref(PreferenciaPagoDto pref) {
		this.pref = pref;
	}

	public VentaDto getVenta(){
		return venta;
	}

	public void setVenta(VentaDto venta) {
		this.venta = venta;
	}

	public List<ProductoModel> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoModel> productos) {
		this.productos = productos;
	}

	@Override
	public String toString() {
		return "DetalleMail [email=" + email + ", fecha=" + fecha + ", idVenta=";
		//		+ idVenta + ", idArticulo=" + idArticulo + ", cantidad=" + cantidad + ", precioConImpuesto="
		//		+ precioConImpuesto + ", descuentoPorcentual=" + descuentoPorcentual + ", precioConDescuento="
	//			+ precioConDescuento + ", precioFinal=" + precioFinal + ", idDireccion=" + idDireccion + "]";
	}

	

}