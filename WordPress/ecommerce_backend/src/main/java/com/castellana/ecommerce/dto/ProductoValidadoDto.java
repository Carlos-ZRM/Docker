package com.castellana.ecommerce.dto;

public class ProductoValidadoDto {
	
	private int idArticulo;
	
	private int cantidad;

	public int getIdArticulo() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo = idArticulo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "productoValidadoDto [idArticulo=" + idArticulo + ", cantidad=" + cantidad + "]";
	}

}
