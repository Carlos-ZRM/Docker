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
@Table(name = "EcommerceCostosEnvio")
public class CostosEnvioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "idCostosEnvio")
	private int id;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "zona")
	private int zona;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoUnKilo")
	private Double costoUnKilo;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoKiloAdicional")
	private Double costoKiloAdicional;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoCincoKilo")
	private Double costoCincoKilo;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoKiloAdicionalMasDeCinco")
	private Double costoKiloAdicionalMasDeCinco;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoCombustible")
	private Double costoCombustible;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "costoReexpedicion")
	private Double costoReexpedicion;
	
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getZona() {
		return zona;
	}

	public void setZona(int zona) {
		this.zona = zona;
	}

	public Double getCostoUnKilo() {
		return costoUnKilo;
	}

	public void setCostoUnKilo(Double costoUnKilo) {
		this.costoUnKilo = costoUnKilo;
	}

	public Double getCostoKiloAdicional() {
		return costoKiloAdicional;
	}

	public void setCostoKiloAdicional(Double costoKiloAdicional) {
		this.costoKiloAdicional = costoKiloAdicional;
	}

	public Double getCostoCincoKilo() {
		return costoCincoKilo;
	}

	public void setCostoCincoKilo(Double costoCincoKilo) {
		this.costoCincoKilo = costoCincoKilo;
	}

	public Double getCostoKiloAdicionalMasDeCinco() {
		return costoKiloAdicionalMasDeCinco;
	}

	public void setCostoKiloAdicionalMasDeCinco(Double costoKiloAdicionalMasDeCinco) {
		this.costoKiloAdicionalMasDeCinco = costoKiloAdicionalMasDeCinco;
	}

	public Double getCostoCombustible() {
		return costoCombustible;
	}

	public void setCostoCombustible(Double costoCombustible) {
		this.costoCombustible = costoCombustible;
	}

	public Double getCostoReexpedicion() {
		return costoReexpedicion;
	}

	public void setCostoReexpedicion(Double costoReexpedicion) {
		this.costoReexpedicion = costoReexpedicion;
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

	@Override
	public String toString() {
		return "CostosEnvioModel [id=" + id + ", zona=" + zona + ", costoUnKilo=" + costoUnKilo
				+ ", costoKiloAdicional=" + costoKiloAdicional + ", costoCincoKilo=" + costoCincoKilo
				+ ", costoKiloAdicionalMasDeCinco=" + costoKiloAdicionalMasDeCinco + ", costoCombustible="
				+ costoCombustible + ", costoReexpedicion=" + costoReexpedicion + ", borrado=" + borrado
				+ ", fechaElimino=" + fechaElimino + ", usuarioElimino=" + usuarioElimino + ", fechaEdicion="
				+ fechaEdicion + ", usuarioEdicion=" + usuarioEdicion + "]";
	}

}
