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

import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.models.ProductoCarritoModel;
import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.repository.UsuarioRepository;
import com.castellana.ecommerce.dto.BotellasCalculoEventoDto;


@Repository
public class UsuarioRepositoryImpl extends JdbcDaoSupport implements UsuarioRepository {
	
	private static final Logger log = LoggerFactory.getLogger(UsuarioRepositoryImpl.class);
	
	@Value("${aws.bucket.articulos.urls}")
	private String bucket;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public int registraUsuario(UsuarioModel usuario) {
		String sql = String.format("INSERT INTO EcommerceUsuario (nombre, appaterno, apmaterno, email, password)"
				+ " VALUES ('%s', '%s', '%s', '%s', sha('%s'))", 
				usuario.getNombre(), usuario.getAppaterno(), usuario.getApmaterno(), usuario.getEmail(), usuario.getPassword());
		return getJdbcTemplate().update(sql);
	}

	@Override
	public int verificaCorreo(String correo) {
		String sql = String.format("SELECT idUsuario "
				+ "FROM EcommerceUsuario WHERE email = '%s'", correo);
		try {
			int idUsr = getJdbcTemplate().queryForObject(sql, Integer.class);
			return idUsr;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public UsuarioModel datosUsuario(String correo) {
		String sql = "SELECT idUsuario, nombre, appaterno, apmaterno "
				+ "FROM EcommerceUsuario WHERE email = ?; ";
		try {
			return (UsuarioModel) getJdbcTemplate().queryForObject(sql, new Object[] { correo }, new RowMapper<UsuarioModel>() {
				@Override
				public UsuarioModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					UsuarioModel u = new UsuarioModel();
					u.setIdUsuario((int)rs.getInt("idUsuario"));
					u.setNombre(rs.getString("nombre"));
					u.setAppaterno(rs.getString("appaterno"));
					u.setApmaterno(rs.getString("apmaterno"));
					return u;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int updatePasswordUsuario(int idUsuario, String newPassword) {
		int update = 0;
		log.info("ID USR: " + idUsuario);
		log.info("NEW PWD: " + newPassword);
		String sql = String.format("UPDATE EcommerceUsuario SET password = SHA('%s') " 
				+ " WHERE idUsuario = %d;", newPassword, idUsuario);
		log.info("QUERY: " + sql);
		update = getJdbcTemplate().update(sql);
		log.info("ES EL INT DEL UPDATE: -----> " + update);
		return update;
	}
	
	@Override
	public int suscripcionActivaNewsletter(String correo) {
		try {
			
		String sql = String.format("SELECT count(email) FROM EcommerceRegistroNewsLetter where email = '%s';", correo);
		
		int idUsr = getJdbcTemplate().queryForObject(sql, Integer.class);
		log.info("BANDERA DE LA BASE PARA NL: " + idUsr);
		return idUsr;
		
		} catch (Exception e) {
			return 0;
		}
	
	}

	@Override
	public int insertarUsuarioNL(String mail) {
		int insert = 0;
		
		try { 
			String sql = String.format("INSERT INTO EcommerceRegistroNewsLetter (email, fechaRegistro) " 
					+ "VALUES ( '%s',now());", mail);
			
			insert = getJdbcTemplate().update(sql);
			
			return insert;
		} catch (Exception e) {
			return insert;
		}
		
	}
	

	@Override
	public List<ProductoCarritoModel> existeEnCarrito(int idToken, int idArticulo) {
		String sql = String.format("SELECT * FROM VentasExpontaneas WHERE idToken = '%d' AND idProductoDeCarro = '%d' AND borrado = 0;",
				idToken, idArticulo);
		List<ProductoCarritoModel> listProductos = new ArrayList<ProductoCarritoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
		
		try {
			for (Map<String, Object> row: rows) {
				ProductoCarritoModel p = new ProductoCarritoModel();
				p.setId((int) row.get("id"));
				p.setIdToken((String) row.get("idToken"));
				p.setIdProducto((String) row.get("idProductoDeCarro"));
				p.setCantidad((int) row.get("cantidad"));
				listProductos.add(p);
			}
			return listProductos;
		} catch (Exception e) {
			return null;
		}
	}

	
	@Override
	public int insertarArticuloCarrito(int idToken, int idArticulo, int cantidad) {
		try {
			String sql = String.format("INSERT INTO VentasExpontaneas (idToken, idProductoDeCarro, cantidad, status, idPagoMP) "
					+ "VALUES ('%s', '%s', '%s', 'no confirmado', NULL);", idToken, idArticulo, cantidad);
			return getJdbcTemplate().update(sql);
		} catch (Exception e) {
			return 0;
		}
	
	}

	@Override
	public int deleteArticuloCarrito(int idToken, int idVenta) {
		int update = 0;
		try {
			String sql = String.format("UPDATE VentasExpontaneas SET borrado = 1 "
					+ "WHERE idToken = '%s'AND  id = '%s' AND borrado = 0;", 
					idToken, idVenta);
			update = getJdbcTemplate().update(sql);
			return update;
		} catch (Exception e) {
			return update;
		}
	}

	@Override
	public int actualizarCantidadArticuloCarrito(int idVenta, int cantidad) {
		int update = 0;
		try {
			String sql = String.format("UPDATE VentasExpontaneas SET cantidad = '%s' where id = '%s'", 
					cantidad, idVenta);
			update=getJdbcTemplate().update(sql);
			return update;
		} catch (Exception e) {
			return update;
		}
	}

	@Override
	public List<ProductoModel> getArticulosCarrito(int token) {
		List<ProductoModel> productos = new ArrayList<ProductoModel>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT \n"
				+ "ve.id AS idVentaExpontanea, ve.idToken, vea.idArticulo, vea.idCategoria, ve.cantidad, a.peso, ve.borrado, ve.status, "
				+ "vea.descripcionArticulo, vea.imagen1, vea.inventario, vea.volumen, vea.nombreUnidadMedida, "
				+ "vea.pvpSinImp AS pvpSinImp, vea.ivaTasa 'ivaTasa', vea.iepsTasa 'iepsTasa', vea.pvpConImp AS pvpConImp, "
				+ "vea.porcentajeDescuento, vea.pvpConDesc AS pvpConDesc, ve.idProductoDeCarro "
				+ "FROM \n"
				+ "VentasExpontaneas ve \n"
				+ "INNER JOIN V_EcommerceArticulos vea on ve.idProductoDeCarro = vea.idArticulo \n"
				+ "INNER JOIN  Articulo a ON ve.idProductoDeCarro = a.idArticulo \n"
				+ "WHERE ve.idToken = " + token + " and ve.borrado = 0;");
		
		try {
			for (Map<String, Object> row: rows) {
				ProductoModel p = new ProductoModel();
				ProductoCarritoModel pc = new ProductoCarritoModel();

				p.setId((Integer) row.get("idArticulo"));
				p.setDescripcion((String) row.get("descripcionArticulo"));
				p.setImagen1(bucket+ row.get("imagen1"));
				p.setInventario((Integer) row.get("inventario"));
				p.setVolumen(Double.parseDouble((String) row.get("volumen")));
				p.setPvpSinImp( new BigDecimal((Double) row.get("pvpSinImp")).setScale(0, RoundingMode.UP));
				p.setIvaTasa(((BigDecimal) row.get("ivaTasa")).setScale(2, RoundingMode.UP));
				p.setIepsTasa(new BigDecimal((Double) row.get("iepsTasa")).setScale(3, RoundingMode.HALF_EVEN));
				p.setPvpConImp(new BigDecimal((Double) row.get("pvpConImp")).setScale(0, RoundingMode.UP));
				p.setPorcentajeDescuento(new BigDecimal((Float) row.get("porcentajeDescuento")).setScale(2, RoundingMode.HALF_EVEN));
				p.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				p.setIdCategoria((Integer) row.get("idCategoria"));
				pc.setId((Integer) row.get("idVentaExpontanea"));
				pc.setPeso((String) row.get("peso"));
				pc.setIdToken((String) row.get("idToken"));
				pc.setIdProducto((String) row.get("idProductoDeCarro"));
				pc.setCantidad((Integer) row.get("cantidad"));
				pc.setBorrado((Integer) row.get("borrado"));
				pc.setStatus((String) row.get("status"));
				
				p.setProductoCarrito(pc);
				productos.add(p);
			}
			return productos;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int incrementaCantidadArticulosCarrito(int idTocken, int idArticulo, int idCantidad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public BotellasCalculoEventoDto getBotellasCalculoEvento(int personasPorEvento) {
        BotellasCalculoEventoDto bce = new BotellasCalculoEventoDto();
        try {
			String sql = String.format("SELECT * FROM EcommerceCalculoEventos WHERE personasPorEvento = %s ;",
			personasPorEvento);
			log.info("QUERY EVENTOS " + sql);
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            
            for (Map<String, Object> row: rows) {
                bce.setPersonasEvento((Integer)row.get("personasPorEvento"));
                bce.setTequila_mezcal((Integer)row.get("tequila_mezcal"));
                bce.setRon((Integer)row.get("ron"));
                bce.setBrandy_cognac((Integer)row.get("brandy_cognac"));
                bce.setWhisky((Integer)row.get("whisky"));
                bce.setVodka((Integer)row.get("vodka"));
                bce.setVinoBlanco_Rosado((Integer)row.get("vinoBlanco_Rosado"));
                bce.setVinoTinto((Integer)row.get("vinoTinto"));
                bce.setChampagne_espumoso((Integer)row.get("champagne_espumoso"));
                bce.setAnis((Integer)row.get("anis"));
                bce.setLicor_crema((Integer)row.get("licor_crema"));
                bce.setGinebra((Integer)row.get("ginebra"));
                bce.setRefrescos((Integer)row.get("refrescos"));
                bce.setCerveza((Integer)row.get("cerveza"));
                bce.setHielo5kg((Integer)row.get("hielo5kg"));
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {

        }
        return bce;
    }

}
