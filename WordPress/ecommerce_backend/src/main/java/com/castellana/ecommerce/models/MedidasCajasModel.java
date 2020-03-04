package com.castellana.ecommerce.models;

import java.sql.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "EcommerceMedidasCajas")
public class MedidasCajasModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idMedidasCajas")
	private int id;

	@Basic(optional = false)
	@NotNull
	@Column(name = "botellas")
	private int botellas;

	@Basic(optional = false)
	@NotNull
	@Column(name = "lago")
	private double lago;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ancho")
	private double ancho;

	@Basic(optional = false)
	@NotNull
	@Column(name = "alto")
	private double alto;

	@Basic(optional = false)
	@NotNull
	@Column(name = "peso")
	private double peso;

	@Basic(optional = false)
	@NotNull
	@Column(name = "borrado")
	private int borrado;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaElimino")
	private Date fechaElimino;

	@Basic(optional = false)
	@NotNull
	@Column(name = "usuarioElimino")
	private int usuarioElimino;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaEdicion")
	private Date fechaEdicion;

	@Basic(optional = false)
	@NotNull
	@Column(name = "usuarioEdicion")
	private int usuarioEdicion;

	@Basic(optional = false)
	@NotNull
	@Column(name = "fechaRegistro")
	private Date fechaRegistro;

	@Basic(optional = false)
	@NotNull
	@Column(name = "usuarioRegistro")
	private int usuarioRegistro;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBotellas() {
		return botellas;
	}

	public void setBotellas(int botellas) {
		this.botellas = botellas;
	}

	public double getLago() {
		return lago;
	}

	public void setLago(double lago) {
		this.lago = lago;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public double getAlto() {
		return alto;
	}

	public void setAlto(double alto) {
		this.alto = alto;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public int getBorrado() {
		return borrado;
	}

	public void setBorrado(int borrado) {
		this.borrado = borrado;
	}

	public Date getFechaElimino() {
		return fechaElimino;
	}

	public void setFechaElimino(Date fechaElimino) {
		this.fechaElimino = fechaElimino;
	}

	public int getUsuarioElimino() {
		return usuarioElimino;
	}

	public void setUsuarioElimino(int usuarioElimino) {
		this.usuarioElimino = usuarioElimino;
	}

	public Date getFechaEdicion() {
		return fechaEdicion;
	}

	public void setFechaEdicion(Date fechaEdicion) {
		this.fechaEdicion = fechaEdicion;
	}

	public int getUsuarioEdicion() {
		return usuarioEdicion;
	}

	public void setUsuarioEdicion(int usuarioEdicion) {
		this.usuarioEdicion = usuarioEdicion;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public int getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(int usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	@Override
	public String toString() {
		return "MedidasCajasModel [id=" + id + ", botellas=" + botellas + ", lago=" + lago + ", ancho=" + ancho
				+ ", alto=" + alto + ", peso=" + peso + ", borrado=" + borrado + ", fechaElimino=" + fechaElimino
				+ ", usuarioElimino=" + usuarioElimino + ", fechaEdicion=" + fechaEdicion + ", usuarioEdicion="
				+ usuarioEdicion + ", fechaRegistro=" + fechaRegistro + ", usuarioRegistro=" + usuarioRegistro + "]";
	}

}
