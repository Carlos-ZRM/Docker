package com.castellana.ecommerce.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.repository.DireccionUsuarioRepository;
import com.castellana.ecommerce.service.DireccionUsuarioService;

@Service
public class DireccionUsuarioServiceImpl implements DireccionUsuarioService{
	
	private static final Logger LOG = LoggerFactory.getLogger(DireccionUsuarioServiceImpl.class);

	@Autowired
	DireccionUsuarioRepository direccionRepo;
	
	@Override
	public boolean addDireccion(DireccionUsuarioModel direccion) {
		boolean val;
		int in = direccionRepo.addDireccion(direccion);
		
		if (in != 0) {
			val = true;
		} else {
			val = false;
		}
		LOG.info("REALIZO LA INSERCION: " + val);
		return val;
	}

	@Override
	public List<DireccionUsuarioModel> getDirecciones(int idUsuario) {
		List<DireccionUsuarioModel> direcciones = direccionRepo.getDirecciones(idUsuario);
		LOG.info("SE OBTUVIERON " + direcciones.size() + "DIRECCIONES DE ESTE USUARIO");
		return direcciones;
	}

	@Override
	public boolean actualizarBorradoDireccion(int idDireccion) {
		int update = direccionRepo.actualizarBorradoDireccion(idDireccion);
		if (update != 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public DireccionUsuarioModel getDetalleDireccion(int idDireccion) {
		DireccionUsuarioModel d = new DireccionUsuarioModel();
		LOG.info("SERVICE SE CONSULTAR LA DIRECCION ID: " + idDireccion);
		d = direccionRepo.getDetalleDireccion(idDireccion);
		LOG.info("CALLE " + d.getCalle() + ", Num Ext " + d.getNumExterior() + ", Num Int " + d.getNumInterior() + ", COLONIA " + d.getColonia());
		return d;
	}

	@Override
	public boolean updateDireccion(DireccionUsuarioModel d) {
		LOG.info("ACTUALIZA DIRECCION ID: " + d.getIdDireccion());
		int update = direccionRepo.updateDireccion(d);
		if (update != 0) {
			LOG.info("SE REALIZO EL UPDATE DE LA DIRECCION");
			return true;
		} else {
			LOG.info("NO SE PUDO REALIZAR EL UPDATE");
			return false;
		}
		
	}

	@Override
	public boolean existenciaDireccion(int idUsuario) {
		boolean existe = false;
		
		int num = direccionRepo.existenDirecciones(idUsuario);
		
		if (num != 0 ) {
			existe = true;
		} 
		
		LOG.info("EXISTEN DIRECCIONES PARA EL USUARIO " + idUsuario + ": " + existe);
		
		return existe;
	}

	@Override
	public boolean validaCP(String CP) {
		boolean val = false;
		int num = direccionRepo.validaCP(CP);
		if (num == 1) {
			val = true;
		}
		return val;
	}

}
