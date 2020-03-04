package com.castellana.ecommerce.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.ArticuloRepository;
import com.castellana.ecommerce.repository.UsuarioRepository;
import com.castellana.ecommerce.service.ArticuloService;

@Service
public class ArticuloServiceImpl implements ArticuloService{
	
	@Autowired
	private ArticuloRepository articuloRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;

	private final static Logger LOG = LoggerFactory.getLogger(ArticuloServiceImpl.class);
	
	@Override
	public List<ProductoModel> getArticulos(int categoriaCarrusel) {
		List<ProductoModel> productos = articuloRepository.getArticulos(categoriaCarrusel);
		
		if (!productos.isEmpty()) {
			return productos;
		} else {
			return null;
		}
	}

	@Override
	public List<ProductoModel> filtroArticulos(int idCategoria, Integer idAniada, Integer precioMin, Integer precioMax) {
		if (idAniada == null) {
			idAniada = 0;
		}
		
		if(precioMin==null) {
			precioMin = 0;
		}
		
		if(precioMax== null) {
			precioMax = 10000;
		}
		
		List <ProductoModel> productos = articuloRepository.getArticulosFiltrados(idCategoria, idAniada, precioMin, precioMax);
		return productos;
	}

	@Override
	public List<ProductoModel> productoPaqueteBodas(int idCat) {
		List<ProductoModel> productos = articuloRepository.getPaquetesBodas(idCat);
		return productos;
	}
	
	@Override
	public ProductoModel getDetalleProducto(int id) {
		ProductoModel p = articuloRepository.getDetalleArticulo(id);
		return p;
	}

	@Override
	public List<Object> verificaExistenciaProductos(int idToken) {
		
		
		List<Object> lista = new ArrayList<Object>();
		List<ProductoModel> productos = articuloRepository.getArticulosEnExistencia(idToken);
		int borrado = 0;
		int actualizado = 0;
		boolean cambios = false; 
		
		for (ProductoModel p: productos) {
			if (p.getInventario() == 0 ) {
				borrado =+ usuarioRepository.deleteArticuloCarrito( Integer.parseInt(p.getProductoCarrito().getIdToken()), p.getProductoCarrito().getId());
				LOG.info(borrado + " ARTICULO(S) ELIMINADOS DEL CARRITO");
			} else if (p.getInventario() < p.getProductoCarrito().getCantidad()) {
				actualizado =+ articuloRepository.actualizarStatus(Integer.parseInt(p.getProductoCarrito().getIdToken()), p.getProductoCarrito().getId(), 
						p.getInventario(), "actualizado");
				LOG.info(actualizado + " ARTICULO(S) ACTUALIZADOS DEL CARRITO");
			} else {
				int confirmado =+ articuloRepository.actualizarStatus(Integer.parseInt(p.getProductoCarrito().getIdToken()), p.getProductoCarrito().getId(), 
						p.getProductoCarrito().getCantidad(), "confirmado");
				LOG.info(confirmado + " ARTICULO(S) CONFIRMADOS DEL CARRITO");
			}
		}
		
		productos = articuloRepository.getArticulosEnExistencia(idToken);
		
		lista.add(productos);
		
		if ( borrado != 0 || actualizado != 0) {
			cambios = true;
		}
		
		lista.add(cambios);

		return lista;
	}

	@Override
	public int validaInventario(int idArt) {
		int inventario = 0;
		inventario = articuloRepository.validaInventario(idArt);
		return inventario;
	}

	@Override
	public int cantidadArticuloCarrito(int token, int idArt) {
		int cantidad = 0;
		cantidad = articuloRepository.cantidadArticuloCarrito(token, idArt);
		return cantidad;
	}

}
