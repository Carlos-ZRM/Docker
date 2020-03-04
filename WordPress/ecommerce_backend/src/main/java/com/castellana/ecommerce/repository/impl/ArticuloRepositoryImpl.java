package com.castellana.ecommerce.repository.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.models.ProductoCarritoModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.ArticuloRepository;

@Repository
public class ArticuloRepositoryImpl extends JdbcDaoSupport implements ArticuloRepository{
	
	
	@Value("${aws.bucket.articulos.urls}")
	private String bucket;
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}
	
	private static final Logger LOG = LoggerFactory.getLogger(ArticuloRepositoryImpl.class);
	
	@Override
	public List<ProductoModel> getArticulos(int categoriaCarrusel) {
		
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT \n"
                + "    vea.idArticulo,\n"
                + "    vea.descripcionArticulo,\n"
                + "    vea.volumen,\n"
                + "    vea.imagen1,\n"
                + "    vea.inventario,\n"
                + "    vea.pvpSinImp,\n"
                + "    vea.iepsTasa,\n"
                + "    vea.ivaTasa,\n"
                + "    vea.pvpConImp,\n"
                + "    vea.porcentajeDescuento,\n"
                + "    vea.pvpConDesc\n"
                + "FROM\n"
                + "    V_EcommerceArticulos vea\n"
                + "WHERE\n"
                + "    vea.categoriaCarrusel = " + categoriaCarrusel + " and vea.inventario != 0");
		
		try {
			for (Map<String, Object> row : rows) {
				ProductoModel p = new ProductoModel();
				
				p.setId((Integer) row.get("idArticulo"));
				p.setDescripcion((String) row.get("descripcionArticulo"));
				p.setVolumen(Double.parseDouble((String) row.get("volumen")));
				p.setImagen1(bucket + row.get("imagen1"));
				p.setInventario((Integer) row.get("inventario"));
				p.setPvpSinImp(new BigDecimal((Double) row.get("pvpSinImp")).setScale(0, RoundingMode.UP));
				p.setIepsTasa(new BigDecimal((Double) row.get("iepsTasa")).setScale(3, RoundingMode.HALF_EVEN));
				p.setIvaTasa(((BigDecimal) row.get("ivaTasa")).setScale(2, RoundingMode.UP));
				p.setPvpConImp(new BigDecimal((Double) row.get("pvpConImp")).setScale(0, RoundingMode.UP));
				p.setPorcentajeDescuento(new BigDecimal((Float) row.get("porcentajeDescuento")).setScale(2, RoundingMode.HALF_EVEN));
				p.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				
				productos.add(p);
			}
			return productos;
		}catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<ProductoModel> getArticulosFiltrados(int idCategoria, int idAniada, int precioMin, int precioMax) {
		
		String aniada = "\n";
		
		if (idAniada !=0 ) {
			aniada = " AND idAniada = " + idAniada + " \n ";
		}
		
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * \n"
				+ "FROM \n"
				+ "V_EcommerceArticulos \n"
				+ "WHERE \n"
				+ "inventario != 0 and idCategoria = " + idCategoria + "\n" 
				+ aniada
				+ "AND pvpConDesc BETWEEN " + precioMin + " AND " + precioMax + ";");
		
		try {
			for(Map<String, Object> row : rows) {
				ProductoModel p = new ProductoModel();
				
				p.setId((Integer) row.get("idArticulo"));
				p.setDescripcion((String) row.get("descripcionArticulo"));
				p.setVolumen(Double.parseDouble((String) row.get("volumen")));
				p.setImagen1(bucket +row.get("imagen1"));
				p.setInventario((Integer) row.get("inventario"));
				p.setPvpSinImp( new BigDecimal((Double) row.get("pvpSinImp")).setScale(0, RoundingMode.UP));
				p.setIepsTasa(new BigDecimal((Double) row.get("iepsTasa")).setScale(3, RoundingMode.HALF_EVEN));
				p.setIvaTasa(((BigDecimal) row.get("ivaTasa")).setScale(2, RoundingMode.UP));
				p.setPvpConImp(new BigDecimal((Double) row.get("pvpConImp")).setScale(0, RoundingMode.UP));
				p.setPorcentajeDescuento(new BigDecimal((Float) row.get("porcentajeDescuento")).setScale(2, RoundingMode.HALF_EVEN));
				p.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				p.setIdAniada((int) row.get("idAniada"));
				p.setAniada((String) row.get("nombreAniada"));
				p.setIdCategoria((int) row.get("idCategoria"));
				p.setCategoria((String) row.get("nombreCategoria"));
				p.setIdSupercategoria((int) row.get("idSupercategoria"));
				p.setSupercategoria((String) row.get("nombreSupercategoria"));
				
				productos.add(p);
			}
			return productos;
		}catch (Exception e) {
			return null;
		}

	}
	

	@Override
	public List<ProductoModel> getPaquetesBodas(int idCategoria) {

		
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * \n"
				+ "FROM \n"
				+ "V_EcommerceArticulos \n"
				+ "WHERE \n"
				+ "inventario != 0 and idCategoria = " + idCategoria + "\n" 
				+ "ORDER by descripcionArticulo asc;");
		
		try {
			for(Map<String, Object> row : rows) {
				ProductoModel p = new ProductoModel();
				
				p.setId((Integer) row.get("idArticulo"));
				p.setDescripcion((String) row.get("descripcionArticulo"));
				p.setVolumen(Double.parseDouble((String) row.get("volumen")));
				p.setImagen1(bucket +row.get("imagen1"));
				p.setInventario((Integer) row.get("inventario"));
				p.setPvpSinImp( new BigDecimal((Double) row.get("pvpSinImp")).setScale(0, RoundingMode.UP));
				p.setIepsTasa(new BigDecimal((Double) row.get("iepsTasa")).setScale(3, RoundingMode.HALF_EVEN));
				p.setIvaTasa(((BigDecimal) row.get("ivaTasa")).setScale(2, RoundingMode.UP));
				p.setPvpConImp(new BigDecimal((Double) row.get("pvpConImp")).setScale(0, RoundingMode.UP));
				p.setPorcentajeDescuento(new BigDecimal((Float) row.get("porcentajeDescuento")).setScale(2, RoundingMode.HALF_EVEN));
				p.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				p.setIdAniada((int) row.get("idAniada"));
				p.setAniada((String) row.get("nombreAniada"));
				p.setIdCategoria((int) row.get("idCategoria"));
				p.setCategoria((String) row.get("nombreCategoria"));
				p.setIdSupercategoria((int) row.get("idSupercategoria"));
				p.setSupercategoria((String) row.get("nombreSupercategoria"));
				
				productos.add(p);
			}
			LOG.info("RETURN CONSULTA: " + productos.toString());
			return productos;
		}catch (Exception e) {
			return null;
		}
	}


	@Override
	public ProductoModel getDetalleArticulo(int id) {
		String sql = "SELECT * FROM V_EcommerceArticulos WHERE inventario != 0 AND idArticulo = ?;";
		LOG.info("SQL: " + sql); 
		try {
			return (ProductoModel) getJdbcTemplate().queryForObject(sql, new Object[] { id }, new RowMapper<ProductoModel>() {

				@Override
				public ProductoModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					ProductoModel p = new ProductoModel();
					
					p.setId((Integer) rs.getInt("idArticulo"));
					p.setDescripcion(rs.getString("descripcionArticulo"));
					p.setImagen1(bucket + rs.getString("imagen1"));
					p.setInventario((Integer) rs.getInt("inventario"));
					p.setIvaTasa((BigDecimal) rs.getBigDecimal("ivaTasa").setScale(2, RoundingMode.UP));
					p.setIepsTasa(new BigDecimal((Double) rs.getDouble("iepsTasa")).setScale(3, RoundingMode.HALF_EVEN));
					p.setPvpSinImp( new BigDecimal((Double) rs.getDouble("pvpSinImp")).setScale(0, RoundingMode.UP));
					p.setPvpConImp(new BigDecimal((Double) rs.getDouble("pvpConImp")).setScale(0, RoundingMode.UP));
					p.setPorcentajeDescuento(new BigDecimal((Float) rs.getFloat("porcentajeDescuento")).setScale(2, RoundingMode.HALF_EVEN));
					p.setPvpConDesc(new BigDecimal((Double) rs.getDouble("pvpConDesc")).setScale(0, RoundingMode.UP));
					p.setIdSupercategoria((int) rs.getInt("idSupercategoria"));
					p.setSupercategoria(rs.getString("nombreSupercategoria"));
					p.setIdCategoria((int) rs.getInt("idCategoria"));
					p.setCategoria(rs.getString("nombreCategoria"));
					p.setIdAniada((int) rs.getInt("idAniada"));
					p.setAniada(rs.getString("nombreAniada"));
					
					p.setDetalle(rs.getString("detalleArticulo")); 
	                if(p.getDetalle().equals("")){
	                    p.setDetalle(rs.getString("descripcionArticulo"));
	                }
	                if(p.getAniada().equals("NO ESPECIFICADO")){
	                    p.setAniada(p.getCategoria());
	                }
					
					return p;
				}
				
			});
			
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public List<ProductoModel> getArticulosEnExistencia(int idToken) {
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT \n"
				+ "ve.id, ve.idToken, ve.idProductoDeCarro, ve.cantidad, ve.borrado, ve.status, \n" 
				+ "vea.descripcionArticulo,  vea.inventario, \n" 
				+ "vea.imagen1,  vea.pvpConDesc \n"
				+ "FROM \n" 
				+ "VentasExpontaneas ve \n" 
				+ "INNER JOIN V_EcommerceArticulos vea ON ve.idProductoDeCarro = vea.idArticulo \n" 
				+ "WHERE ve.idToken = " + idToken + " AND ve.borrado = 0;");
		try {
			for(Map<String, Object> row : rows) {
				ProductoModel pm = new ProductoModel();
				ProductoCarritoModel pcm = new ProductoCarritoModel();
				
				pm.setDescripcion((String) row.get("descripcionArticulo"));
				pm.setInventario((Integer) row.get("inventario"));
				pm.setImagen1(bucket + (String) row.get("imagen1"));
				pm.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				
				pcm.setId((Integer) row.get("id"));
				pcm.setIdToken((String) row.get("idToken"));
				pcm.setIdProducto((String) row.get("idProductoDeCarro"));
				pcm.setCantidad((Integer) row.get("cantidad"));
				pcm.setBorrado((Integer) row.get("borrado"));
				pcm.setStatus((String) row.get("status"));
				
				
				pm.setProductoCarrito(pcm);
				
				productos.add(pm);
			}
			return productos;
		} catch (Exception e) {
			LOG.info("ERROR " + e);
			return null;
		}
		
	}

	@Override
	public int actualizarStatus(int idToken, int idVenta, int cantidad, String status) {
		int update = 0;
		try {
			String sql = String.format("UPDATE VentasExpontaneas SET status = '%s',  cantidad = '%s' where idToken = '%s' and id = '%s';", 
					status, cantidad, idToken, idVenta);
			update = getJdbcTemplate().update(sql);
			return update;
		} catch (Exception e) {
			return update;
		}	
	}

	@Override
	public int validaInventario(int idArt) {
		int retorno = 0;
		
		LOG.info("Valida invetario");
		String sql = String.format("SELECT inventario FROM V_EcommerceArticulos WHERE idArticulo = " + idArt + ";");
		LOG.info(sql);
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		if (!rows.isEmpty()) {
			for (Map<String, Object> row: rows) {
				retorno = (int) row.get("inventario");
			}
		}
		
		return retorno;
	}

	@Override
	public int cantidadArticuloCarrito(int token, int idArt) {
		int retorno = 0;
		
		LOG.info("CAntidad de articulo en el carrito");
		String sql = String.format("SELECT cantidad FROM VentasExpontaneas where idToken = "+ token
				+ " AND idProductoDeCarro = " + idArt + " AND borrado = 0;");
		LOG.info(sql);
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		if (!rows.isEmpty()) {
			for (Map<String, Object> row: rows) {
				retorno = (int) row.get("cantidad");
			}
		}
		
		return retorno;
	}

}
