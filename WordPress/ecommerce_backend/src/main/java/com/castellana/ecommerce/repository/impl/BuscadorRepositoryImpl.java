package com.castellana.ecommerce.repository.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.BuscadorRepository;

@Repository
public class BuscadorRepositoryImpl extends JdbcDaoSupport implements BuscadorRepository {
	
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
	public List<ProductoModel> search(String cadena) {
		List <ProductoModel> productos = new ArrayList<ProductoModel>();
		ProductoModel p;
		
		//Se convierte a un arreglo para poder separar la cadena
		String[] parts = cadena.split(" ");
		int n = parts.length;
		StringBuilder sb = new StringBuilder();
		int is = 0;
		LOG.info("Cadena a buscar: " +  cadena);
		LOG.info("ARRAY : " + n);
		
		if (cadena.contains("vin")) {
			sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
					+ "  FROM  V_EcommerceArticulos vea WHERE idSupercategoria = 8 ");
            is = 2;
		}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if (cadena.equals("vt") || cadena.equals("v.tto") || cadena.equals("VT") || cadena.equals("V.TTO")) {
			sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
					+ "FROM  V_EcommerceArticulos vea \n"
					+ "WHERE idSupercategoria = 8 AND idCategoria = 68");
			is = 1;
		} else if (cadena.equals("vb") || cadena.equals("v.bco") || cadena.equals("VB") || cadena.equals("V.BCO")) {
			sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
					+ " FROM  V_EcommerceArticulos vea \n"
					+ "WHERE idSupercategoria = 8 AND idCategoria = 10");
			is = 1;
		} else if (cadena.equals("vr") || cadena.equals("VR")) {
			sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
					+ " FROM  V_EcommerceArticulos vea \n"
					+ "WHERE idSupercategoria = 8 AND idCategoria = 58");
			is = 1;
		}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		else if (cadena.contains("anis")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 7");
            is = 1;
        } else if (cadena.contains("aperi")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 8");
            is = 1;
        } else if (cadena.contains("armag")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 9");
            is = 1;
        } else if (cadena.contains("bran")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 12");
            is = 1;
        } else if (cadena.contains("calv")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 14");
            is = 1;
        } else if (cadena.contains("cogn")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 21");
            is = 1;
        } else if (cadena.contains("crem")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 29");
            is = 1;
        } else if (cadena.contains("gin")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 37");
            is = 1;
        } else if (cadena.contains("jer")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 41");
            is = 1;
        } else if (cadena.contains("lico")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 46");
            is = 1;
        } else if (cadena.contains("mez")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 47");
            is = 1;
        } else if (cadena.contains("pisc")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 51");
            is = 1;
        } else if (cadena.contains("rom")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 56");
            is = 1;
        } else if (cadena.contains("ron")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 57");
            is = 1;
        } else if (cadena.contains("sak")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 59");
            is = 1;
        } else if (cadena.contains("sotol")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 64");
            is = 1;
        } else if (cadena.contains("teq")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 67");
            is = 1;
        } else if (cadena.contains("vod")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 70");
            is = 1;
        } else if (cadena.contains("whi")) {
            sb.append("SELECT vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc \n"
            		+ " FROM  V_EcommerceArticulos vea \n"
                    + "WHERE idSupercategoria = 6 AND idCategoria = 71");
            is = 1;
        } else {
            LOG.info("LA ULTIMA CONDICION");
            sb.append("SELECT \n"
                            + "    vea.idArticulo, vea.descripcionArticulo, vea.imagen1, vea.pvpConImp, vea.porcentajeDescuento, vea.pvpConDesc\n"
                            + "FROM\n"
                            + "    V_EcommerceArticulos vea\n"
                            + "WHERE\n"
                            + " vea.descripcionArticulo LIKE '%" + cadena + "%'");
        }
        LOG.info("Is -----> " + is);
        
        if (is == 1 && n > 1) {
            sb.append(" AND vea.descripcionArticulo LIKE ");
            for (int i = 1; i < n; i++) {
                sb.append("'%" + parts[i] + "%' ");
                if (i != (n - 1)) {
                    sb.append(" AND vea.descripcionArticulo LIKE ");
                }
            }
        }
        
        if (is == 2 && n>1){
            if (cadena.contains("blan") || cadena.contains("vb") || cadena.contains("v.bco") || cadena.contains("VB") || cadena.contains("V.BCO")){
                sb.append(" AND vea.idCategoria = 10");
            }
            else if (cadena.contains("cava")){
                 sb.append(" AND vea.idCategoria = 16");
            }
            else if (cadena.contains("cham")){
                 sb.append(" AND vea.idCategoria = 18");
            }
            else if (cadena.contains("esp")){
                 sb.append(" AND vea.idCategoria = 34");
            }
            else if (cadena.contains("gen")){
                 sb.append(" AND vea.idCategoria = 36");
            }
            else if (cadena.contains("jer")){
                 sb.append(" AND vea.idCategoria = 40");
            }
            else if (cadena.contains("opo")){
                 sb.append(" AND vea.idCategoria = 48");
            }
            else if (cadena.contains("rosa") || cadena.contains("vr") || cadena.contains("VR")){
                 sb.append(" AND vea.idCategoria = 58");
            }
            else if (cadena.contains("sid")){
                 sb.append(" AND vea.idCategoria = 63");
            }
            else if (cadena.contains("tin") || cadena.contains("vt") || cadena.contains("v.tto") || cadena.contains("VT") || cadena.contains("V.TTO")){
                 sb.append(" AND vea.idCategoria = 68");
            }
            else{
              sb.append(" AND vea.descripcionArticulo LIKE ");
                for (int i = 1; i < n; i++) {
                    sb.append("'%" + parts[i] + "%' ");
                        if (i != (n - 1)) {
                    sb.append(" AND vea.descripcionArticulo LIKE ");
                    }
                }  
            }
            is = 3;
        }
        
        if (is == 3 && n > 2) {
            sb.append(" AND vea.descripcionArticulo LIKE ");
            for (int i = 2; i < n; i++) {
                sb.append("'%" + parts[i] + "%' ");
                if (i != (n - 1)) {
                    sb.append(" AND vea.descripcionArticulo LIKE ");
                }
            }
        }
		
//        sb.append(" and idSupercategoria != 9 ");
		sb.append(" and vea.inventario != 0;");
		
		LOG.info("QUERY DE BUSQUEDA: " + sb.toString());
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sb.toString());
		
		LOG.info("ROWS: "  + rows.size());
		try {
			for(Map<String, Object> row : rows) {
				p = new ProductoModel();
				
				p.setId((Integer) row.get("idArticulo"));
				p.setImagen1(bucket + ((String) row.get("imagen1")));
				p.setDescripcion((String) row.get("descripcionArticulo"));
				p.setPvpConImp(new BigDecimal((Double) row.get("pvpConImp")).setScale(0, RoundingMode.UP));
				p.setPorcentajeDescuento(new BigDecimal((Float) row.get("porcentajeDescuento")).multiply(new BigDecimal(100)).setScale(0, RoundingMode.UP));
				p.setPvpConDesc(new BigDecimal((Double) row.get("pvpConDesc")).setScale(0, RoundingMode.UP));
				LOG.info("IDs: " + p.getId());
				productos.add(p);
			
			}
			return productos;
		} catch (Exception e) {
			LOG.info("ERROR: " + e);
			e.printStackTrace();
			return null;
		}
	}

}
