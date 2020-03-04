package com.castellana.ecommerce.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.repository.PruebaRepository;

@Repository
public class PruebaRepositoryImpl extends JdbcDaoSupport implements PruebaRepository {

	private static final Logger LOG = LoggerFactory.getLogger(PruebaRepositoryImpl.class);
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}
	
	@Override
	public int insertVenta(VentaDto venta) {
		String sql = "INSERT INTO EcommerceVentas \n"
				+ "(idUsuario, idDireccion, fechaCreacion, estatusPago, estatusEnvio, estatusPortal, costoEnvio, "
				+ "idMercadoPago, estatusPagoDetalle, total, servicioEnvio, pesoEnvio) \n "
				+ "VALUES \n "
				+ "(?, ?, now(), ?, '', ?, ?, ?, ?, ?, ?, ?)";
		KeyHolder keyholder = new GeneratedKeyHolder();
		
		try {
			
			jdbcTemplate.update(
					new PreparedStatementCreator() {
						
						@Override
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement(sql, new String[] {"idVenta"});
							
							ps.setInt(1, venta.getIdUsuario());
				            ps.setInt(2, venta.getIdDireccion());
				            ps.setString(3, venta.getEstatusPago());
				            ps.setInt(4, venta.getIdEstatusPortal());
				            ps.setBigDecimal(5, venta.getCostoEnvio());
				            ps.setLong(6, venta.getIdMercadoPago());
				            ps.setString(7, venta.getEstatusPagoDetalle());
				            ps.setBigDecimal(8, venta.getTotal());
				            ps.setInt(9, venta.getServicioEnvio());
				            ps.setBigDecimal(10, venta.getPesoEnvio());
				            LOG.info("EL ID DE LA VENTA : " + ps);
							return ps;
						}
					}, keyholder);
			LOG.info("EL ID DE LA VENTA : " + keyholder.getKey());
			return (keyholder.getKey()).intValue();
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.fillInStackTrace();
			return 0;
		}
	}

}