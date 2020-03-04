package com.castellana.ecommerce.models;

public class ProductoCarritoModel {
	private int id;
	private String idToken;
	private String idProducto;
	private int cantidad;
	private int borrado;
	private String status;
	private String peso;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIdToken() {
		return idToken;
	}
	public void setIdToken(String idToken) {
		this.idToken = idToken;
	}
	public String getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public int getBorrado() {
		return borrado;
	}
	public void setBorrado(int borrado) {
		this.borrado = borrado;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPeso() {
		return peso;
	}
	public void setPeso(String peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "ProductoCarritoModel [id=" + id + ", idToken=" + idToken + ", idProducto=" + idProducto + ", cantidad="
				+ cantidad + ", borrado=" + borrado + ", status=" + status + ", peso=" + peso + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + borrado;
		result = prime * result + cantidad;
		result = prime * result + id;
		result = prime * result + ((idProducto == null) ? 0 : idProducto.hashCode());
		result = prime * result + ((idToken == null) ? 0 : idToken.hashCode());
		result = prime * result + ((peso == null) ? 0 : peso.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductoCarritoModel other = (ProductoCarritoModel) obj;
		if (borrado != other.borrado)
			return false;
		if (cantidad != other.cantidad)
			return false;
		if (id != other.id)
			return false;
		if (idProducto == null) {
			if (other.idProducto != null)
				return false;
		} else if (!idProducto.equals(other.idProducto))
			return false;
		if (idToken == null) {
			if (other.idToken != null)
				return false;
		} else if (!idToken.equals(other.idToken))
			return false;
		if (peso == null) {
			if (other.peso != null)
				return false;
		} else if (!peso.equals(other.peso))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	
}