package com.castellana.ecommerce.models;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EcommerceDirecciones")
public class DireccionUsuarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idDireccion")
	private int idDireccion;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "idCliente")
	private int idCliente;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "tipo")
	private String tipo;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "destinatario")
	private String destinatario;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "calle")
	private String calle;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "numExterior")
	private String numExterior;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "numInterior")
	private String numInterior;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "estado")
	private String estado;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "ciudad")
	private String ciudad;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "municipio")
	private String municipio;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "colonia")
	private String colonia;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "codigoPostal")
	private String codigoPostal;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "numeroTelefonico")
	private String numeroTelefonico;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "extensionTelefonica")
	private String extensionTelefonica;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "referencia")
	private String referencia;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "borrado")
	private int borrado;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "razonSocial")
	private String razonSocial;

	public int getIdDireccion() {
		return idDireccion;
	}

	public void setIdDireccion(int idDireccion) {
		this.idDireccion = idDireccion;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getCalle() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle = calle;
	}

	public String getNumExterior() {
		return numExterior;
	}

	public void setNumExterior(String numExterior) {
		this.numExterior = numExterior;
	}

	public String getNumInterior() {
		return numInterior;
	}

	public void setNumInterior(String numInterior) {
		this.numInterior = numInterior;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getColonia() {
		return colonia;
	}

	public void setColonia(String colonia) {
		this.colonia = colonia;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getNumeroTelefonico() {
		return numeroTelefonico;
	}

	public void setNumeroTelefonico(String numeroTelefonico) {
		this.numeroTelefonico = numeroTelefonico;
	}

	public String getExtensionTelefonica() {
		return extensionTelefonica;
	}

	public void setExtensionTelefonica(String extensionTelefonica) {
		this.extensionTelefonica = extensionTelefonica;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public int getBorrado() {
		return borrado;
	}

	public void setBorrado(int borrado) {
		this.borrado = borrado;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

}
