package com.castellana.ecommerce.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.dto.CostosDto;
import com.castellana.ecommerce.dto.DetalleVentaDto;
import com.castellana.ecommerce.dto.UsuarioDto;
import com.castellana.ecommerce.dto.pago.PaqueteEstafetaDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.VentaLDto;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.VentasRepository;

@Repository
public class VentasRepositoryImpl extends JdbcDaoSupport implements VentasRepository {
	
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
	
	private static final Logger LOG = LoggerFactory.getLogger(VentasRepositoryImpl.class); 
	
	@Override
	public int getBotellasCarrito(int idToken) {
		try {
			String sql = String.format("SELECT SUM(cantidad) total FROM VentasExpontaneas WHERE idToken = '%s' and borrado = 0", idToken);
			LOG.info("SQL botellas: " + sql);
			int cantidad = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("EL CARRITO CONTIENE " + cantidad + " BOTELLA(S).");
			return cantidad;
		} catch (Exception e) {
			LOG.error("ERROR getBotellasCarrito: " + e);
			return 0;
		}
	}
	
	@Override
	public int getBotellasCarritoCalculoEnvio(int idToken) {
		try {
			String sql = String.format("SELECT SUM(cantidad) total FROM VentasExpontaneas ve \n"
					+ "INNER JOIN Articulo a ON ve.idProductoDeCarro = a.idArticulo \n"
					+ "WHERE ve.idToken = '%s' \n"
//					+ "AND a.idSupercategoria != 9 \n"
					+ "AND ve.borrado = 0;", idToken);
			LOG.info("SQL botellas: " + sql);
			int cantidad = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("EL CARRITO CONTIENE " + cantidad + " BOTELLA(S).");
			return cantidad;
		} catch (Exception e) {
			LOG.error("ERROR getBotellasCarrito: " + e);
			return 0;
		}
	}

	@Override
	public int getZona(String cpDestino) {
		try {
			String sql = String.format("SELECT zona FROM EcommerceCodigoPostalZona WHERE codigoPostal = '%s';", cpDestino);
			int zona = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("LA ZONA ES: " + zona);
			return zona;
		} catch (Exception e) {
			LOG.error("ERROR getZona: " + e);
			return 0;
		}
	}

	@Override
	public CostosDto getCostosEnvio(String codigoPostal, int zona, int numBotellas) {
		String sql = String.format("SELECT "
				+ "cpz.codigoPostal, "
				+ "ce.zona, "
				+ "mc.botellas, "
				+ "cpz.reexpedicion, "
				+ "mc.peso, "
				+ "ce.costoUnKilo, "
				+ "ce.costoKiloAdicional, "
				+ "ce.costoCincoKilo, "
				+ "ce.costoKiloAdicionalMasDeCinco, "
				+ "ce.costoCombustible, "
				+ "ce.costoReexpedicion "
				+ "FROM "
				+ "EcommerceMedidasCajas mc, "
				+ "EcommerceCostosEnvio ce INNER JOIN EcommerceCodigoPostalZona cpz ON ce.zona = cpz.zona "
				+ "WHERE "
				+ "mc.botellas = ? AND ce.zona = ? AND cpz.codigoPostal = ?;"); 
		try {
			return (CostosDto) getJdbcTemplate().queryForObject(sql, new Object[] {numBotellas, zona, codigoPostal}, new RowMapper<CostosDto>() {
				
				@Override
				public CostosDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CostosDto c = new CostosDto();
					c.setCodigoPostal((int) rs.getInt("codigoPostal"));
	                c.setZona((int) rs.getInt("zona"));
	                c.setNumBotellas((int) rs.getInt("botellas"));
	                c.setReexpedicion((int) rs.getInt("reexpedicion"));
	                c.setPeso((double) Math.round(rs.getDouble("peso") * 100d) / 100d);
	                c.setCostoUnKilo((double) rs.getDouble("costoUnKilo"));
	                c.setCostoKiloAdicional((double) rs.getDouble("costoKiloAdicional"));
	                c.setCostoCincoKilos((double) rs.getDouble("costoCincoKilo"));
	                c.setCostoKiloAdicionalMasDeCinco((double) rs.getDouble("costoKiloAdicionalMasDeCinco"));
	                c.setCostoCombustible((double) rs.getDouble("costoCombustible"));
	                c.setCostoReexpedicion((double) rs.getDouble("costoReexpedicion"));
					return c;
				}
				
			});
			
		} catch (Exception e) {
			LOG.error("ERROR getCostosEnvio : " + e);
			return null;
		}
	}
	

	@Override
	public CostosDto getCostosEnvio12mas(String codigoPostal, int zona, int numBotellas) {
		String sql = String.format("SELECT \n" 
				+ "cpz.codigoPostal, \n" 
				+ "ce.zona, \n" 
				+ "mc.botellas, \n" 
				+ "cpz.reexpedicion, \n" 
				+ "mc.peso, \n" 
				+ "ce.costoUnKilo, \n" 
				+ "ce.costoKiloAdicional, \n" 
				+ "ce.costoCincoKilo, \n" 
				+ "ce.costoKiloAdicionalMasDeCinco, \n" 
				+ "ce.costoCombustible, \n" 
				+ "ce.costoReexpedicion \n" 
				+ "FROM \n" 
				+ "EcommerceMedidasCajas mc, \n" 
				+ "EcommerceCostosEnvio ce inner join EcommerceCodigoPostalZona cpz on ce.zona = cpz.zona \n" 
				+ "WHERE \n" 
				+ "mc.botellas = 1 and ce.zona = ? and cpz.codigoPostal = ?;");
		try {
			return (CostosDto) getJdbcTemplate().queryForObject(sql, new Object[] {zona, codigoPostal}, new RowMapper<CostosDto>() {
				
				@Override
				public CostosDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CostosDto c = new CostosDto();
					c.setCodigoPostal((int) rs.getInt("codigoPostal"));
	                c.setZona((int) rs.getInt("zona"));
	                c.setNumBotellas((int) rs.getInt("botellas"));
	                c.setReexpedicion((int) rs.getInt("reexpedicion"));
	                c.setPeso((double) rs.getDouble("peso"));
	                c.setCostoUnKilo((double) rs.getDouble("costoUnKilo"));
	                c.setCostoKiloAdicional((double) rs.getDouble("costoKiloAdicional"));
	                c.setCostoCincoKilos((double) rs.getDouble("costoCincoKilo"));
	                c.setCostoKiloAdicionalMasDeCinco((double) rs.getDouble("costoKiloAdicionalMasDeCinco"));
	                c.setCostoCombustible((double) rs.getDouble("costoCombustible"));
	                c.setCostoReexpedicion((double) rs.getDouble("costoReexpedicion"));
					return c;
				}
				
			});
			
		} catch (Exception e) {
			LOG.error("ERROR getCostosEnvio12mas: " + e);
			return null;
		}
	}

	@Override
	public String getPrecioEnvioGratis() {
		String response = "";
		
		String sql = String.format("SELECT precio FROM EnvioGratis ORDER BY idEnviosGratis DESC limit 1");
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);
			if (!rows.isEmpty()) {
				for (Map<String, Object> row: rows) {
					response = Double.toString( (Double) row.get("precio"));
				}
			}
			return response;
		} catch (Exception e) {
			LOG.error("ERROR getPrecioEnvioGratis: " + e);
			return null;
		}	
	}

	@Override
	public PaqueteEstafetaDto getPaqueteEstafeta(int numBotellas) {
		String sql = String.format("SELECT botellas, largo, ancho, alto, peso \n"
				+ "FROM EcommerceMedidasCajas WHERE botellas = ? AND borrado = 0;");
		try {
			return (PaqueteEstafetaDto) getJdbcTemplate().queryForObject(sql, new Object[] {numBotellas}, new RowMapper<PaqueteEstafetaDto>() {
				@Override
				public PaqueteEstafetaDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					PaqueteEstafetaDto p = new PaqueteEstafetaDto();
					p.setBotellas(rs.getInt("botellas"));
	                p.setLargo(rs.getDouble("largo"));
	                p.setAncho(rs.getDouble("ancho"));
	                p.setAlto(rs.getDouble("alto"));
	                p.setPeso(rs.getDouble("peso"));
	                LOG.info("PAQUETE ESTAFETA : " + p);
					return p;
				}
			});
		} catch (Exception e) {
			LOG.error("ERROR getPaqueteEstafeta: " + e);
			return null;
		}
	}

	@Override
	public int getIdTipoEnvioEstafeta(String tipoServicio) {
		String sql = String.format("SELECT tipoEnvioEstafeta FROM EcommerceTipoEnvio WHERE tipoEnvio = '" + tipoServicio + "';");
		LOG.info("TIPO ENVIO SQL: " + sql);
		try {
			int tipoEnvio = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("TIPO ENVIO ESTAFETA: " + tipoEnvio);
			return tipoEnvio;
		} catch (Exception e) {
			LOG.error("ERROR getIdTipoEnvioEstafeta: " + e);
			return 0;
		}
	}

	@Override
	public UsuarioDto getUsuarioPorIdDireccion(int idDireccion) {
		String sql = String.format("SELECT u.idUsuario, u.nombre, u.appaterno, u.apmaterno, u.email, u.fechaRegistro \n" 
				+ "FROM EcommerceDirecciones d \n" 
				+ "INNER JOIN EcommerceUsuario u ON d.idCliente = u.idUsuario \n" 
				+ "WHERE idDireccion = ? ;");
		LOG.info("BUSCA USUARIO: " + sql);
		try {
			return (UsuarioDto) getJdbcTemplate().queryForObject(sql, new Object[] {idDireccion}, new RowMapper<UsuarioDto>() {

				@Override
				public UsuarioDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					UsuarioDto u = new UsuarioDto();
					u.setIdUsuario(rs.getInt("idUsuario"));
					u.setNombre(rs.getString("nombre"));
					u.setAppaterno(rs.getString("appaterno"));
					u.setApmaterno(rs.getString("apmaterno"));
					u.setEmail(rs.getString("email"));
					u.setFechaRegistro(rs.getString("fechaRegistro"));
					return u;
				}
				
			});
			
		} catch (Exception e) {
			LOG.error("ERROR " + e);
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public int registrarVenta(VentaDto venta) {
		try {
			String sql = "INSERT INTO EcommerceVentas \n"
					+ "(idUsuario, idDireccion, fechaCreacion, estatusPago, estatusEnvio, estatusPortal, costoEnvio, "
					+ "idMercadoPago, estatusPagoDetalle, total, servicioEnvio, pesoEnvio) \n "
					+ "VALUES \n "
					+ "(?, ?, now(), ?, '', ?, ?, ?, ?, ?, ?, ?)";
			KeyHolder keyholder = new GeneratedKeyHolder();
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
			LOG.info("EL ID DE LA VENTA : " + (keyholder.getKey()).intValue());
			return (keyholder.getKey()).intValue();
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.fillInStackTrace();
			return 0;
		}

	}

	@Override
	public int registrarVentaArticulo(ProductoModel p, int idVenta) {
		try {
			String sql = String.format("INSERT INTO EcommerceVentaArticulo \n" 
					+ "(idVenta, idArticulo, cantidad, precioSinImp, descuento, precioConImp) \n" 
					+ "VALUES \n" 
					+ "(?, ?, ?, ?, ?, ?);");
//					, 
//					idVenta,
//					p.getId(),
//					p.getProductoCarrito().getCantidad(),
//					p.getPvpSinImp(),
//					p.getPorcentajeDescuento(),
//					p.getPvpConDesc());
			
			
			KeyHolder keyholder = new GeneratedKeyHolder();
			jdbcTemplate.update(
					new PreparedStatementCreator() {
						
						@Override
						public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
							PreparedStatement ps = connection.prepareStatement(sql, new String[] {"idVenta"});
							
							ps.setInt(1, idVenta);
				            ps.setInt(2, p.getId());
				            ps.setInt(3, p.getProductoCarrito().getCantidad());
				            ps.setBigDecimal(4, p.getPvpSinImp());
				            ps.setBigDecimal(5, p.getPorcentajeDescuento());
				            ps.setBigDecimal(6, p.getPvpConDesc());
				            
				            LOG.info("EL ID DEl ARTICULO VENDIDO : " + ps);
							return ps;
						}
					}, keyholder);
			LOG.info("SE INSERTA VENTA ARTICULO: " + (keyholder.getKey()).intValue());
			return (keyholder.getKey()).intValue();
			
			
//			LOG.info("SE INSERTA VENTA ARTICULO: " + sql);
//			return getJdbcTemplate().update(sql);
		} catch (Exception e) {
			LOG.info("ERROR: " + e);
			return 0;
		}
		
	}

	@Override
	public List<Integer> getIdVentasExpontaneas(int idToken) {
		List<Integer> id = new ArrayList<Integer>();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT id FROM VentasExpontaneas WHERE idToken = "+ idToken+";");
		try {
			for(Map<String, Object> row : rows) {
				Integer i = ((Integer) row.get("id"));
				id.add(i);
			}
			LOG.info("ARRAY DE ID: " + id);
			return id;
		} catch (Exception e) {
			LOG.info("ERROR: " + e);
			return null;
		}
	}

	@Override
	public List<VentaLDto> getComprasCliente(int idCliente) {
		List<VentaLDto> ventas = new ArrayList<VentaLDto>();
		VentaLDto v;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT \n"
				+ "    v.idVenta,\n"
                + "    v.idUsuario,\n"
                + "    v.fechaCreacion fechaCreacion,\n"
                + "    'TARJETA DE CR�DITO' metodoPago,\n"
                + "    (SELECT \n"
                + "            SUM(va.precioConImp * va.cantidad)\n"
                + "        FROM\n"
                + "            EcommerceVentaArticulo va\n"
                + "        WHERE\n"
                + "            va.idVenta = v.idVenta) subtotal,\n"
                + "    v.costoEnvio,\n"
                + "    (SELECT subtotal) + v.costoEnvio AS total,\n"
                + "    v.estatusPortal,\n"
                + "    ev.estatusVenta, \n"
                + "    ed.calle \n"
                + "FROM\n"
                + "    EcommerceVentas v\n"
                + "        INNER JOIN EcommerceEstatusVentas ev ON v.estatusPortal = ev.idEstatusVenta\n"
                + "    	   INNER JOIN EcommerceDirecciones ed ON v.idUsuario = ed.idCliente \n"
                + "WHERE\n"
                + "    v.idUsuario = " + idCliente + "\n"
                + "GROUP BY v.idVenta ORDER BY v.idVenta DESC");
		
		try {
			for (Map<String, Object> row: rows) {
				v = new VentaLDto();
				
				Timestamp ts = (Timestamp) row.get("fechaCreacion");
				Date fecha = new Date(ts.getTime());
				
				v.setIdVenta((Integer) row.get("idVenta"));
                v.setFechaCreacion(fecha.toString());
                v.setMetodoPago((String) row.get("metodoPago"));
                v.setSubtotal(new BigDecimal((Double) row.get("subtotal")));
                v.setCostoEnvio(new BigDecimal((Double) row.get("costoEnvio")));
                v.setTotal(new BigDecimal((Double) row.get("total")));
                v.setEstatusPortal((String) row.get("estatusVenta"));
                v.setCalleDomicilio((String) row.get("calle"));
                
                ventas.add(v);
			}
			return ventas;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<DetalleVentaDto> getDetalleVenta(int idVenta) {
		List<DetalleVentaDto> detalleVenta = new ArrayList<DetalleVentaDto>();
		DetalleVentaDto a;
		List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT "
				+ "vea.imagen1, vea.descripcionArticulo, \n" 
				+ "va.idVenta, va.idArticulo, va.cantidad, \n"
				+ "vea.pvpConImp, va.descuento, va.precioConImp,\n" 
				+ "ev.idDireccion, ev.costoEnvio, ev.total \n" 
				+ "FROM \n"
				+ "EcommerceVentaArticulo va \n"
				+ "INNER JOIN V_EcommerceArticulos vea ON va.idArticulo = vea.idArticulo \n"
				+ "INNER JOIN EcommerceVentas ev ON va.idVenta = ev.idVenta \n" 
				+ "WHERE \n" 
				+ "va.idVenta = "+ idVenta + ";");
		
		try {
			for (Map<String, Object> row: rows) {
				a = new DetalleVentaDto();
				
				BigDecimal precioUnitario = new BigDecimal((Double) row.get("precioConImp"));
				BigDecimal cantidad = new BigDecimal((Integer) row.get("cantidad"));
				
				BigDecimal precioTotal = precioUnitario.multiply(cantidad);
				
				a.setImagen1(bucket + ((String) row.get("imagen1")));
				a.setDescripcionArticulo((String) row.get("descripcionArticulo"));
				a.setIdVenta((Integer) row.get("idVenta"));
				a.setIdArticulo((Integer) row.get("idArticulo"));
				a.setCantidad((Integer) row.get("cantidad"));
				a.setPrecioConImpuesto(new BigDecimal((Double) row.get("pvpConImp")));
				a.setDescuentoPorcentual(new BigDecimal((Double) row.get("descuento")));
				a.setPrecioConDescuento(new BigDecimal((Double) row.get("precioConImp")));
				a.setPrecioEnvio(new BigDecimal((Double) row.get("costoEnvio")));
				a.setTotalVenta(new BigDecimal((Double) row.get("total")));
				a.setIdDireccion((Integer) row.get("idDireccion"));
				a.setPrecioFinal(precioTotal);
				
				detalleVenta.add(a);
				
			}
			return detalleVenta;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BigDecimal getPeso(int token) {
		BigDecimal peso = new BigDecimal(0);
		try {
		
			String sql = String.format("SELECT SUM(a.peso * ve.cantidad) peso FROM VentasExpontaneas ve  \n"
					+ "INNER JOIN Articulo a ON ve.idProductoDeCarro = a.idArticulo \n"
					+ "WHERE ve.idToken = '%s' AND ve.borrado = 0 AND a.idCategoria = 74;", token);
			LOG.info("SQL peso: " + sql);
			int p = getJdbcTemplate().queryForObject(sql, Integer.class);
			LOG.info("EL CARRITO TIENE UN PESO DE  " + p + " UNIDADES.");
			
			peso.add(new BigDecimal(p));
			
			return peso;
		} catch (Exception e) {
			LOG.error("ERROR: " + e);
			e.printStackTrace();
			return peso;
		}
	}

	@Override
	public CostosDto getCostoForZona(int zona) {
		
		
		String sql = String.format("SELECT * FROM EcommerceCostosEnvio where zona = ?;");
		
		try {
			return (CostosDto) getJdbcTemplate().queryForObject(sql, new Object[] {zona}, new RowMapper<CostosDto>() {

				@Override
				public CostosDto mapRow(ResultSet rs, int rowNum) throws SQLException {
					CostosDto c = new CostosDto();
					c.setCostoUnKilo(rs.getDouble("costoUnKilo"));
	                c.setCostoKiloAdicional(rs.getDouble("costoKiloAdicional"));
	                c.setCostoCincoKilos(rs.getDouble("costoCincoKilo"));
	                c.setCostoKiloAdicionalMasDeCinco(rs.getDouble("costoKiloAdicionalMasDeCinco"));
	                c.setCostoCombustible(rs.getDouble("costoCombustible"));
	                c.setCostoReexpedicion(rs.getDouble("costoReexpedicion"));
					return c;
				}
			});
				
		} catch (Exception e) {
			LOG.info("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
		
	}

}