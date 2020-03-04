package com.castellana.ecommerce.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.dto.ParametrosValCompProdDTO;
import com.castellana.ecommerce.dto.ProductoValidadoDto;
import com.castellana.ecommerce.repository.ValidacionRepository;

@Repository
public class ValidacionRepositoryImpl extends JdbcDaoSupport implements ValidacionRepository{
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	
	private static final Logger LOG = LoggerFactory.getLogger(ValidacionRepositoryImpl.class);

	/*
	 * Se obtienen los productos ya vendidos de determinado cliente
	 */
	@Override
	public List<ProductoValidadoDto> validacionProducto(ParametrosValCompProdDTO parametros) {
		List<ProductoValidadoDto> lista = new ArrayList<ProductoValidadoDto>();
		ProductoValidadoDto producto;
		String art = "(";
		int vueltas = parametros.getIdproductos().size();
		for(int i = 0; i< parametros.getIdproductos().size(); i ++) {
			art += " A.idArticulo = "+parametros.getIdproductos().get(i);
			vueltas--;
			if (vueltas != 0) {
				art += " OR ";
			}
		}
//		art = art.replace(art.substring(art.length()-1), "");
		art += " )";
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT A.idArticulo, count(A.idArticulo) as Cantidad \n" + 
				"FROM EcommerceVentas V\n" + 
				"INNER JOIN EcommerceVentaArticulo A ON V.idVenta = A.idVenta\n" + 
				"WHERE  "
				+ art + "\n" + 
				"AND V.idUsuario = "+ parametros.getIdUsuario()+"\n" + 
				"AND date(fechaCreacion) > '"+ parametros.getFecha() +"'\n" + 
				"group by A.idArticulo;");
		try {
			for(Map<String, Object> row : rows) {
				 producto = new ProductoValidadoDto();
				 
				 producto.setIdArticulo((Integer) row.get("idArticulo"));
				 producto.setCantidad((int)((long) row.get("Cantidad")));
				 
				 lista.add(producto);
			}
			return lista;
		} catch (Exception e) { 	
			LOG.error("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * Se obtienen determinados productos del carrito correspondiente
	 */
	@Override
	public List<Integer> productosCarrito(int idToken) {
		List<Integer> list = new ArrayList<Integer>();
		int idArt;
		String idArticulo = "";
		List<Map<String, Object>> rows =jdbcTemplate.queryForList(""
				+ "SELECT idProductoDeCarro FROM VentasExpontaneas WHERE idToken = "+idToken+" AND borrado = 0;");
		try {
			for(Map<String, Object> row: rows) {
				idArticulo = ((String) row.get("idProductoDeCarro"));
				idArt =  Integer.parseInt(idArticulo);
				
				list.add(idArt);
			}
			return list;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public int deleteArticuloValidacion(int idToken, int idArticulo) {
		int update = 0;
		try {
			String sql = String.format("UPDATE VentasExpontaneas SET borrado = 1 "
					+ "WHERE idToken = '%s'AND  idProductoDeCarro = '%s' AND borrado = 0;", 
					idToken, idArticulo);
			update = getJdbcTemplate().update(sql);
			return update;
		} catch (Exception e) {
			return update;
		}
	}

}
