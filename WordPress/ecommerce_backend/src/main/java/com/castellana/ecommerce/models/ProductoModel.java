package com.castellana.ecommerce.models;

import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Augusto GG
 *
 */
@Entity
//@Table(name = "V_EcommerceArticulos")
public class ProductoModel {

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Basic(optional = false)
//	@Column(name = "idUsuario")
//    private int idVentaExpontanea;
	
	@Id
	@Basic(optional = false)
	@Column(name = "idArticulo")
    private int id;
	
//	@Basic(optional = false)
//	@NotNull
//	@Column(name = "nombre")
//    private String titulo;//*
    
	@Basic(optional = false)
	@NotNull
	@Column(name = "inventario")
	private int inventario;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "pvpSinImp", insertable = false, updatable = false)
    private double precio;
	
//	@Basic(optional = false)
//	@NotNull
//	@Column(name = "nombre")
//    private double precioDescuento;
	
//	@Basic(optional = false)
//	@NotNull
//	@Column(name = "porcentajeDescuento")
//    private double descuento;
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "descripcionArticulo")
    private String descripcion;
	
	@Basic(optional = false)
	@Column(name = "detalleArticulo")
    private String detalle;
	
	
	@Basic(optional = false)
	@NotNull
	@Column(name = "imagen1")
    private String imagen1;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "volumen")
    private double volumen;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "idAniada")
    private int idAniada;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "idCategoria")
    private int idCategoria;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "idSupercategoria")
    private int idSupercategoria;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "nombreCategoria")
    private String categoria;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "nombreSupercategoria")
    private String supercategoria;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "nombreAniada")
    private String aniada;

    @Basic(optional = false)
	@NotNull
	@Column(name = "ivaTasa")
    private BigDecimal ivaTasa;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "iepsTasa")
    private BigDecimal iepsTasa;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "pvpSinImp")
    private BigDecimal pvpSinImp;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "pvpConImp")
    private BigDecimal pvpConImp;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "porcentajeDescuento")
    private BigDecimal porcentajeDescuento;
    
    @Basic(optional = false)
	@NotNull
	@Column(name = "pvpConDesc")
    private BigDecimal pvpConDesc;
    
    @Transient
    private ProductoCarritoModel productoCarrito;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInventario() {
		return inventario;
	}

	public void setInventario(int inventario) {
		this.inventario = inventario;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public String getImagen1() {
		return imagen1;
	}

	public void setImagen1(String imagen1) {
		this.imagen1 = imagen1;
	}

	public double getVolumen() {
		return volumen;
	}

	public void setVolumen(double volumen) {
		this.volumen = volumen;
	}

	public int getIdAniada() {
		return idAniada;
	}

	public void setIdAniada(int idAniada) {
		this.idAniada = idAniada;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdSupercategoria() {
		return idSupercategoria;
	}

	public void setIdSupercategoria(int idSupercategoria) {
		this.idSupercategoria = idSupercategoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getSupercategoria() {
		return supercategoria;
	}

	public void setSupercategoria(String supercategoria) {
		this.supercategoria = supercategoria;
	}

	public String getAniada() {
		return aniada;
	}

	public void setAniada(String aniada) {
		this.aniada = aniada;
	}

	public BigDecimal getIvaTasa() {
		return ivaTasa;
	}

	public void setIvaTasa(BigDecimal ivaTasa) {
		this.ivaTasa = ivaTasa;
	}

	public BigDecimal getIepsTasa() {
		return iepsTasa;
	}

	public void setIepsTasa(BigDecimal iepsTasa) {
		this.iepsTasa = iepsTasa;
	}

	public BigDecimal getPvpSinImp() {
		return pvpSinImp;
	}

	public void setPvpSinImp(BigDecimal pvpSinImp) {
		this.pvpSinImp = pvpSinImp;
	}

	public BigDecimal getPvpConImp() {
		return pvpConImp;
	}

	public void setPvpConImp(BigDecimal pvpConImp) {
		this.pvpConImp = pvpConImp;
	}

	public BigDecimal getPorcentajeDescuento() {
		return porcentajeDescuento;
	}

	public void setPorcentajeDescuento(BigDecimal porcentajeDescuento) {
		this.porcentajeDescuento = porcentajeDescuento;
	}

	public BigDecimal getPvpConDesc() {
		return pvpConDesc;
	}

	public void setPvpConDesc(BigDecimal pvpConDesc) {
		this.pvpConDesc = pvpConDesc;
	}

	public ProductoCarritoModel getProductoCarrito() {
		return productoCarrito;
	}

	public void setProductoCarrito(ProductoCarritoModel productoCarrito) {
		this.productoCarrito = productoCarrito;
	}

	@Override
	public String toString() {
		return "ProductoModel [id=" + id + ", inventario=" + inventario + ", precio=" + precio + ", descripcion="
				+ descripcion + ", detalle=" + detalle + ", imagen1=" + imagen1 + ", volumen=" + volumen + ", idAniada="
				+ idAniada + ", idCategoria=" + idCategoria + ", idSupercategoria=" + idSupercategoria + ", categoria="
				+ categoria + ", supercategoria=" + supercategoria + ", aniada=" + aniada + ", ivaTasa=" + ivaTasa
				+ ", iepsTasa=" + iepsTasa + ", pvpSinImp=" + pvpSinImp + ", pvpConImp=" + pvpConImp
				+ ", porcentajeDescuento=" + porcentajeDescuento + ", pvpConDesc=" + pvpConDesc + ", productoCarrito="
				+ productoCarrito + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aniada == null) ? 0 : aniada.hashCode());
		result = prime * result + ((categoria == null) ? 0 : categoria.hashCode());
		result = prime * result + ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((detalle == null) ? 0 : detalle.hashCode());
		result = prime * result + id;
		result = prime * result + idAniada;
		result = prime * result + idCategoria;
		result = prime * result + idSupercategoria;
		result = prime * result + ((iepsTasa == null) ? 0 : iepsTasa.hashCode());
		result = prime * result + ((imagen1 == null) ? 0 : imagen1.hashCode());
		result = prime * result + inventario;
		result = prime * result + ((ivaTasa == null) ? 0 : ivaTasa.hashCode());
		result = prime * result + ((porcentajeDescuento == null) ? 0 : porcentajeDescuento.hashCode());
		long temp;
		temp = Double.doubleToLongBits(precio);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((productoCarrito == null) ? 0 : productoCarrito.hashCode());
		result = prime * result + ((pvpConDesc == null) ? 0 : pvpConDesc.hashCode());
		result = prime * result + ((pvpConImp == null) ? 0 : pvpConImp.hashCode());
		result = prime * result + ((pvpSinImp == null) ? 0 : pvpSinImp.hashCode());
		result = prime * result + ((supercategoria == null) ? 0 : supercategoria.hashCode());
		temp = Double.doubleToLongBits(volumen);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		ProductoModel other = (ProductoModel) obj;
		if (aniada == null) {
			if (other.aniada != null)
				return false;
		} else if (!aniada.equals(other.aniada))
			return false;
		if (categoria == null) {
			if (other.categoria != null)
				return false;
		} else if (!categoria.equals(other.categoria))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (detalle == null) {
			if (other.detalle != null)
				return false;
		} else if (!detalle.equals(other.detalle))
			return false;
		if (id != other.id)
			return false;
		if (idAniada != other.idAniada)
			return false;
		if (idCategoria != other.idCategoria)
			return false;
		if (idSupercategoria != other.idSupercategoria)
			return false;
		if (iepsTasa == null) {
			if (other.iepsTasa != null)
				return false;
		} else if (!iepsTasa.equals(other.iepsTasa))
			return false;
		if (imagen1 == null) {
			if (other.imagen1 != null)
				return false;
		} else if (!imagen1.equals(other.imagen1))
			return false;
		if (inventario != other.inventario)
			return false;
		if (ivaTasa == null) {
			if (other.ivaTasa != null)
				return false;
		} else if (!ivaTasa.equals(other.ivaTasa))
			return false;
		if (porcentajeDescuento == null) {
			if (other.porcentajeDescuento != null)
				return false;
		} else if (!porcentajeDescuento.equals(other.porcentajeDescuento))
			return false;
		if (Double.doubleToLongBits(precio) != Double.doubleToLongBits(other.precio))
			return false;
		if (productoCarrito == null) {
			if (other.productoCarrito != null)
				return false;
		} else if (!productoCarrito.equals(other.productoCarrito))
			return false;
		if (pvpConDesc == null) {
			if (other.pvpConDesc != null)
				return false;
		} else if (!pvpConDesc.equals(other.pvpConDesc))
			return false;
		if (pvpConImp == null) {
			if (other.pvpConImp != null)
				return false;
		} else if (!pvpConImp.equals(other.pvpConImp))
			return false;
		if (pvpSinImp == null) {
			if (other.pvpSinImp != null)
				return false;
		} else if (!pvpSinImp.equals(other.pvpSinImp))
			return false;
		if (supercategoria == null) {
			if (other.supercategoria != null)
				return false;
		} else if (!supercategoria.equals(other.supercategoria))
			return false;
		if (Double.doubleToLongBits(volumen) != Double.doubleToLongBits(other.volumen))
			return false;
		return true;
	}

	

    
}
