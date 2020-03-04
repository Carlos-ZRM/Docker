package com.castellana.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.service.ArticuloService;
import com.castellana.ecommerce.service.BuscadorService;

@RestController
@RequestMapping("api/articulos")
public class ArticuloController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ArticuloController.class);

	@Autowired 
	private ArticuloService articulosService;
	
	@Autowired
	private BuscadorService buscador;
	
    /**
     * Funcion que regresa el array de articulos que pertenecen a la categoria
     * envianda en el parametro
     *
     * @param categoriaCarrousel 1 = Nuevos, 2 = Los m�s vendidos/Recomendaciones, 3 = Promocion
     * @return
     * @throws ParseException
     */
	@GetMapping("/categoriaCarrusel/")
	List<ProductoModel> getArticulos(int categoriaCarrusel){
		List<ProductoModel> productos = articulosService.getArticulos(categoriaCarrusel);
		return productos;
	}
	
	@GetMapping("/categoria/{idCat}/")
	List<ProductoModel> filtroArticulos(@PathVariable(name="idCat") int idCategoria, 
			 Integer idAniada, Integer precioMin, Integer precioMax){
		List<ProductoModel> productos = articulosService.filtroArticulos(idCategoria, idAniada, precioMin, precioMax);
		return productos;
	}
	
	@GetMapping("/paquetesBoda/{idBoda}")
	List<ProductoModel> articulosBoda(@PathVariable(name="idBoda") int idBoda){
		LOG.info("ENTRA ACÁ: " + idBoda);
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		productos = articulosService.productoPaqueteBodas(idBoda);	
		LOG.info("NO DE PAQUETES: " + productos.size());
		return productos;
 	}
	
//	@GetMapping("/detalleArticulo/{idArticulo}/")
	@GetMapping("/detalleArticulo")
//	ProductoModel getDetalleProducto(@RequestParam @PathVariable(name="idArticulo") int id) {
		ProductoModel getDetalleProducto(@RequestParam int id) {
		ProductoModel p = articulosService.getDetalleProducto(id);
		return p;
	}
	
	@GetMapping("/typeahead")
	List<ProductoModel> buscador(String stringSearch) {
		List<ProductoModel> productos = buscador.searchForThypeahead(stringSearch);
//		LOG.info("TAMANIO PRODUCTOS: " + productos.size());
		return productos;
	}
	
	@GetMapping("/inventario/{idArt}")
	List<Integer> validaInvetario(@PathVariable(name="idArt") int id, int idToken) {
		
		List<Integer> respuesta =  new ArrayList<Integer>(); 
		
		int inventario = 0;
		inventario = articulosService.validaInventario(id);
		respuesta.add(inventario);
		int cantidad = 0;
		cantidad = articulosService.cantidadArticuloCarrito(idToken, id);
		respuesta.add(cantidad);
		
		return respuesta;
	}
	
	@GetMapping("/cantidadCarrito/{token}")
	int cantidadArticuloCarrito(@PathVariable(name = "token") int idToken, int idArticulo) {
		int cantidad = 0;
		cantidad = articulosService.cantidadArticuloCarrito(idToken, idArticulo);
		return cantidad;
	}
}
