package com.castellana.ecommerce.repository.impl;

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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.repository.DireccionUsuarioRepository;

@Repository
public class DireccionUsuarioRepositoryImpl extends JdbcDaoSupport implements DireccionUsuarioRepository{
	
	private static final Logger LOG = LoggerFactory.getLogger(DireccionUsuarioRepositoryImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplete;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Override
	public int addDireccion(DireccionUsuarioModel dir) {
		try {
			String sql = String.format("INSERT INTO EcommerceDirecciones \n"
					+ "(idCliente, tipo, destinatario, calle, numExterior, numInterior, estado, ciudad, \n"
					+ " municipio, colonia, codigoPostal, numeroTelefonico, extensionTelefonica, referencia) \n"
					+ "VALUES (%d, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s');", 
					dir.getIdCliente(),
					dir.getTipo(),
					dir.getDestinatario(),
					dir.getCalle(),
					dir.getNumExterior(),
					dir.getNumInterior(),
					dir.getEstado(),
					dir.getCiudad(),
					dir.getMunicipio(),
					dir.getColonia(),
					dir.getCodigoPostal(),
					dir.getNumeroTelefonico(),
					dir.getExtensionTelefonica(),
					dir.getReferencia());
			LOG.info("INSERTO DIRECCION");
			return getJdbcTemplate().update(sql);
		}catch (Exception e) {
			LOG.error("NO PUDO INSERTAR DIRECCION");
			return 0;
		}
	}

	@Override
	public List<DireccionUsuarioModel> getDirecciones(int idUsuario) {
		List<DireccionUsuarioModel> direcciones = new ArrayList<DireccionUsuarioModel>();
		List<Map<String, Object>> rows = jdbcTemplete.queryForList("SELECT * FROM EcommerceDirecciones \n"
				+ " WHERE borrado = 0 and idCliente = "+ idUsuario +";");
		try {
			for (Map<String, Object> row : rows) {
				DireccionUsuarioModel dir = new DireccionUsuarioModel();
				dir.setIdDireccion((int) row.get("idDireccion"));
				dir.setIdCliente((int) row.get("idCliente"));
				dir.setTipo((String) row.get("tipo"));
				dir.setDestinatario((String) row.get("destinatario"));
				dir.setCalle((String) row.get("calle"));
				dir.setNumExterior((String) row.get("numExterior"));
				dir.setNumInterior((String) row.get("numInterior"));
				dir.setColonia((String) row.get("colonia"));
				dir.setMunicipio((String) row.get("municipio"));
				dir.setCiudad((String) row.get("ciudad"));
				dir.setEstado((String) row.get("estado"));
				dir.setCodigoPostal((String) row.get("codigoPostal"));
				dir.setNumeroTelefonico((String) row.get("numeroTelefonico"));
				dir.setReferencia((String) row.get("referencia"));
				dir.setExtensionTelefonica((String) row.get("extensionTelefonica"));
				dir.setRazonSocial((String) row.get("razonSocial"));
				direcciones.add(dir);
			}
			return direcciones;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int actualizarBorradoDireccion(int idDireccion) {
		LOG.info("ID DE LA DIRECCION: " + idDireccion);
		int update = 0;
		try {
			String sql = String.format("UPDATE EcommerceDirecciones SET borrado = 1 WHERE idDireccion = %d;", idDireccion);
			update = getJdbcTemplate().update(sql);
			LOG.info("ACTUALIZACION EXITOSA PARA EL ESTATUS DE BORRADO");
			return update;
		} catch (Exception e) {
			LOG.info("NO SE PUDO REALIZAR LA ACTUALIZACION");
			return update;
		}
	}

	@Override
	public DireccionUsuarioModel getDetalleDireccion(int id) {
		String sql = "SELECT * FROM EcommerceDirecciones WHERE idDireccion = ?;";
		LOG.info("QUERY: " + sql);
		try {
			return (DireccionUsuarioModel) getJdbcTemplate().queryForObject(sql, new Object[] {id}, new RowMapper<DireccionUsuarioModel>() {

				@Override
				public DireccionUsuarioModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					DireccionUsuarioModel d = new DireccionUsuarioModel();
					d.setIdDireccion((Integer) rs.getInt("idDireccion"));
					d.setDestinatario((String) rs.getString("destinatario"));
					d.setTipo((String) rs.getString("tipo"));
	                d.setCalle((String) rs.getString("calle"));
	                d.setNumExterior((String) rs.getString("numExterior"));
	                d.setNumInterior((String) rs.getString("numInterior"));
	                d.setColonia((String) rs.getString("colonia"));
	                d.setMunicipio((String) rs.getString("municipio"));
	                d.setCiudad((String) rs.getString("ciudad"));
	                d.setEstado((String) rs.getString("estado"));
	                d.setReferencia(rs.getString("referencia"));
	                d.setCodigoPostal((String) rs.getString("codigoPostal"));
	                d.setNumeroTelefonico((String) rs.getString("numeroTelefonico"));
	                d.setExtensionTelefonica((String) rs.getString("extensionTelefonica"));
	                LOG.info("SE OBTUVO EL DETALLE DE LA DIRECCION id: " + d.getIdDireccion());
	                return d;
				}
				
			}); 
			
		} catch (Exception e) {
			LOG.info("NO SE ENCONTRO NINGUN REGISTRO CON ESE id");
			return null;
		}
	}

	@Override
	public int updateDireccion(DireccionUsuarioModel d) {
		LOG.info("DIRECCION CON ID: " + d.getIdDireccion());
		int update = 0;
		try {
			String sql = String.format("UPDATE EcommerceDirecciones SET \n"
					+ "tipo = '%s', destinatario = '%s', calle = '%s', numExterior = '%s', numInterior = '%s', estado = '%s', ciudad = '%s', \n"
					+ "municipio = '%s', colonia = '%s', codigoPostal = '%s', numeroTelefonico = '%s', extensionTelefonica = '%s', referencia = '%s' \n"
					+ "WHERE idDireccion = '%s';", 
					d.getTipo(), d.getDestinatario(), d.getCalle(), d.getNumExterior(), d.getNumInterior(), d.getEstado(), d.getCiudad(),
					d.getMunicipio(), d.getColonia(), d.getCodigoPostal(), d.getNumeroTelefonico(), d.getExtensionTelefonica(), d.getReferencia(),
					d.getIdDireccion());
			LOG.info(sql);
			update = getJdbcTemplate().update(sql);
			LOG.info("1.SE REALIZO EL UPDATE, 0.NO SE REALIZO EL UPDATE: " + update);
			return update;
		} catch (Exception e) {
			return update;
		}

	}

	@Override
	public int existenDirecciones(int idUsuario) {
		try {
			String sql = String.format("SELECT count(idDireccion) FROM EcommerceDirecciones WHERE idCliente = '%s' AND borrado = 0", idUsuario);
			
			int dir = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("EL USUARIO TIENE " + dir + " DIRECCIONES REGISTRADAS.");
			return dir;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			return 0;
		}
	}

	@Override
	public int validaCP(String CP) {
		try {
			String sql = String.format("SELECT count(idCodigoPostalZona) FROM EcommerceCodigoPostalZona where codigoPostal = '%s';", Integer.parseInt(CP));
			
			int cp = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("EXISTE CP " + cp);
			return cp;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			return 0;
		}
	}

}
