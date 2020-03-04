package com.castellana.ecommerce.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.castellana.ecommerce.models.UsuarioModel;
import com.castellana.ecommerce.repository.AuthRepository;

@Repository
public class AuthRepositoryImpl extends JdbcDaoSupport implements AuthRepository{
	
	private static final Logger log = LoggerFactory.getLogger(AuthRepositoryImpl.class);
	
	@Autowired
	private DataSource datasource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}

	@Override
	public UsuarioModel login(String usr, String psw) {
		String sql = "SELECT idUsuario, email, password, nombre, appaterno, apmaterno, DATE_FORMAT(fechaRegistro, '%Y-%m-%d') fechaCreacion \n" 
				+ "FROM EcommerceUsuario \n" 
				+ "WHERE email = ? and password = sha(?);";
		try {
			return (UsuarioModel) getJdbcTemplate().queryForObject(sql, new Object[] {usr, psw}, new RowMapper<UsuarioModel>() {

				@Override
				public UsuarioModel mapRow(ResultSet rs, int rowNum) throws SQLException {
					UsuarioModel u = new UsuarioModel();
					
					u.setIdUsuario((Integer) rs.getInt("idUsuario"));
					u.setEmail((String) rs.getString("email"));
					u.setNombre((String) rs.getString("nombre"));
					u.setAppaterno((String) rs.getString("appaterno"));
					u.setApmaterno((String) rs.getString("apmaterno"));
					u.setFechaRegistro ((String) rs.getString("fechaCreacion"));
					
					return u;
				}
				
			});
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public UsuarioModel loadByName(String usr) {
		log.info("UserModel loadByName(String user)");
		String sql = "SELECT * FROM EcommerceUsuario WHERE email = '" + usr + "';";
		log.info(sql);
		List<Map<String, Object>> lista = getJdbcTemplate().queryForList(sql);
		
		UsuarioModel u = new UsuarioModel();
		if (!lista.isEmpty()) {
			log.info("size: " + lista.size());
			for (Map<String, Object> rs: lista) {
				u.setIdUsuario((Integer) rs.get("idUsuario"));
				u.setEmail((String) rs.get("email"));
				u.setNombre((String) rs.get("nombre"));
				u.setAppaterno((String) rs.get("appaterno"));
				u.setApmaterno((String) rs.get("apmaterno"));
				u.setFechaRegistro ((String) rs.get("fechaCreacion"));
				u.setPassword((String) rs.get("password"));
				
				log.info("PASWORD: " + u.getPassword());
			}
		} else {
			return null;
		}
		return u;
	}

	@Override
	public int exist(String user) {
		int retorno = 0;
		
		log.info("Validar si el usuario existe ");
		String sql = String.format("SELECT * EcommerceUsuario WHERE email = '" + user + "';");
		log.info(sql);
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
		if (!rows.isEmpty()) {
			for (Map<String, Object> row: rows) {
				retorno = (int) row.get("idUsuario");
			}
		}
		
		return retorno;
	}

	@Override
	public boolean oldpwdCorrecta(String usr, String oldpwd) {
		log.info("Validar si el password del usuario es correcto");
		String sql = String.format("SELECT * FROM EcommerceUsuario WHERE email = '%s' AND password = sha('%s');", 
				usr, oldpwd);
		log.info(sql);
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
			if(!rows.isEmpty()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

}
