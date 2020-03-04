package com.castellana.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.castellana.ecommerce.dto.CostosDto;
import com.castellana.ecommerce.dto.DetalleVentaDto;
import com.castellana.ecommerce.dto.InfoMailDto;
import com.castellana.ecommerce.dto.RespuestaCotizaEnvioDto;
import com.castellana.ecommerce.dto.TipoServicioDto;
import com.castellana.ecommerce.dto.UsuarioDto;
import com.castellana.ecommerce.dto.pago.AdditionalInfoDto;
import com.castellana.ecommerce.dto.pago.AdditionalInfoTADto;
import com.castellana.ecommerce.dto.pago.AddressDto;
import com.castellana.ecommerce.dto.pago.CardDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoDto;
import com.castellana.ecommerce.dto.pago.CuerpoPagoTarjetaAlmacenadaDto;
import com.castellana.ecommerce.dto.pago.ItemDto;
import com.castellana.ecommerce.dto.pago.PaqueteEstafetaDto;
import com.castellana.ecommerce.dto.pago.PayerDto;
import com.castellana.ecommerce.dto.pago.PayerTA;
import com.castellana.ecommerce.dto.pago.Payer_dto;
import com.castellana.ecommerce.dto.pago.PhoneDto;
import com.castellana.ecommerce.dto.pago.PreferenciaPagoDto;
import com.castellana.ecommerce.dto.pago.PreferenciaPagoTADto;
import com.castellana.ecommerce.dto.pago.RespuestaPagoDto;
import com.castellana.ecommerce.dto.pago.ShipmentsDto;
import com.castellana.ecommerce.dto.pago.VentaDto;
import com.castellana.ecommerce.dto.pago.VentaLDto;
import com.castellana.ecommerce.dto.pago.Customer.CustomerResponseDto;
import com.castellana.ecommerce.externos.MercadoPagoComponent;
import com.castellana.ecommerce.models.DireccionUsuarioModel;
import com.castellana.ecommerce.models.ProductoModel;
import com.castellana.ecommerce.repository.DireccionUsuarioRepository;
import com.castellana.ecommerce.repository.UsuarioRepository;
import com.castellana.ecommerce.repository.VentasRepository;
import com.castellana.ecommerce.service.MailService;
import com.castellana.ecommerce.service.VentasService;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Card;
import com.mercadopago.resources.Customer;
import com.mercadopago.*;

@Service
public class VentasServiceImpl implements VentasService{

	@Value("${mercadoLibre.acces_token}")
	private String accessToken;
	
	@Autowired
	private VentasRepository ventasRep;
	
	@Autowired
	DireccionUsuarioRepository direccionUsrRep;
	
	@Autowired
	private UsuarioRepository usuarioRepo;

	@Autowired
	private MercadoPagoComponent mercadoPago;
	
	@Autowired
	private MailService mail;

	
	private final static Logger LOG = LoggerFactory.getLogger(VentasServiceImpl.class);

	@Override
	public List<Object> consultarCostoEnvio(String cpDestino, int idToken) {
		
		List<Object> res = new ArrayList<Object>();
		
		int zona = 0;
		TipoServicioDto[] tsa = new TipoServicioDto[1];
		RespuestaCotizaEnvioDto respuesta = new RespuestaCotizaEnvioDto();
		
		/**
		 * Obtiene el numero de botellas y el peso total de los paquetes
		 */
		int numeroBotellas = ventasRep.getBotellasCarritoCalculoEnvio(idToken);
		//BigDecimal peso = ventasRep.getPeso(idToken);
		LOG.info("No. botellas: " + numeroBotellas);
		//LOG.info("PESO TOTAL: " + peso);
		
		/**
		 * Consulta zona de envío 
		 */
		if ( !cpDestino.equals("")) {
			zona = ventasRep.getZona(cpDestino);
		}
		
		LOG.info(" --> Con Token: " + idToken + " cotizara numBotellas: " + numeroBotellas + " al CP Destino: " + cpDestino + " zona:" + zona);
		
		
		CostosDto costos = null;
		double pesoXpaq = 0.0;
        double pesoXbot = 0.0;
        List<ProductoModel> productos = usuarioRepo.getArticulosCarrito(idToken);
        double pesoFinal = 0.0;
        int pqt = 0;
        
        for(ProductoModel p: productos) {
        	pqt++;
        	if(p.getIdCategoria()==74) {
        		costos = ventasRep.getCostoForZona(zona);
        		pesoFinal += (Double.parseDouble(p.getProductoCarrito(). getPeso()) * p.getProductoCarrito().getCantidad() );
                pesoXpaq += (Double.parseDouble(p.getProductoCarrito().getPeso()) * p.getProductoCarrito().getCantidad() ); 
        	}
        }
        
        costos = ventasRep.getCostosEnvio12mas(cpDestino, zona, numeroBotellas);
        
        pesoXbot = Math.ceil(costos.getPeso() * numeroBotellas);
        
		
		if (costos.getReexpedicion() == 1) {
			pesoFinal += Math.ceil( 1.4 * numeroBotellas);
			LOG.info("PESO POR BOTELLA: " + "1.4");
		}else{
			pesoFinal += Math.ceil( 1.7 * numeroBotellas);
			LOG.info("PESO POR BOTELLA: " + "1.7");
		}
		
        LOG.info("No. BOTELLAS : " + numeroBotellas + " PESO  " + pesoFinal);
        LOG.info("No. PAQUETES : " + pqt + " PESO  " + pesoXpaq);
        LOG.info("PESO TOTAL  : " + pesoFinal);
		LOG.info("REexpedicion: " + costos.getReexpedicion());
		
        double costoFinal = 0.0;
        
        if (pesoFinal > 5) {
            double pesoAux = pesoFinal - 5;
            if (costos.getReexpedicion() == 0) {
                costoFinal = costos.getCostoCincoKilos() + (pesoAux * costos.getCostoKiloAdicionalMasDeCinco()) + costos.getCostoCombustible();
            } else {
                costoFinal = costos.getCostoCincoKilos() + (pesoAux * costos.getCostoKiloAdicionalMasDeCinco()) + costos.getCostoCombustible() + costos.getCostoReexpedicion();
            }
        } else if (pesoFinal == 5) {
            if (costos.getReexpedicion() == 0) {
                costoFinal = costos.getCostoCincoKilos() + costos.getCostoCombustible();
            } else {
                costoFinal = costos.getCostoCincoKilos() + costos.getCostoCombustible() + costos.getCostoReexpedicion();
            }
        }
        if (pesoFinal > 1 && pesoFinal <= 4) {
            double pesoAux = pesoFinal - 1;
            if (costos.getReexpedicion() == 0) {
                costoFinal = costos.getCostoUnKilo() + (pesoAux * costos.getCostoKiloAdicional()) + costos.getCostoCombustible();
            } else {
                costoFinal = costos.getCostoUnKilo() + (pesoAux * costos.getCostoKiloAdicional()) + costos.getCostoCombustible() + costos.getCostoReexpedicion();
            }
        } else if (pesoFinal == 1) {
            if (costos.getReexpedicion() == 0) {
                costoFinal = costos.getCostoUnKilo() + costos.getCostoCombustible();
            } else {
                costoFinal = costos.getCostoUnKilo() + costos.getCostoCombustible() + costos.getCostoReexpedicion();
            }
        }
		double pesoAux2 = pesoFinal - 1;
        double costoFinalIVA = costoFinal * 1.16;
		LOG.info("costoFinal " + costoFinal);
		LOG.info("costoFinalIVA " + costoFinalIVA);
		LOG.info("-" + costos.getCostoUnKilo());
		LOG.info("peso aux: " + pesoAux2);
		LOG.info("costo Ad: " + costos.getCostoKiloAdicional());
		LOG.info("-" + pesoAux2 * costos.getCostoKiloAdicional());
		LOG.info("-" + costos.getCostoCombustible());
		LOG.info("-" + costos.getCostoReexpedicion());
        for (int i = 0; i < tsa.length; i++) {
            TipoServicioDto ts = new TipoServicioDto();
            ts.setDescripcionServicio("Terrestre");
            ts.setCostoTotal(costoFinalIVA);
            tsa[i] = ts;
            respuesta.setTipoServicio(tsa);
        }
        
        res.add(respuesta);
        
        String precio = ventasRep.getPrecioEnvioGratis();
        
        res.add(precio);
        
		return res;
	}

	@Override
	public RespuestaPagoDto MPCheckoutPersonalizado(CuerpoPagoDto pago) {
		
		UsuarioDto usr = ventasRep.getUsuarioPorIdDireccion(Integer.parseInt(pago.getIdDireccion()));
		LOG.info("Usuario: " + usr);
		DireccionUsuarioModel direccion = direccionUsrRep.getDetalleDireccion(Integer.parseInt(pago.getIdDireccion()));
		LOG.info("Direccion: " + direccion.toString());
		int numeroBotellas = ventasRep.getBotellasCarrito(Integer.parseInt(pago.getTokenCarrito()));
		LOG.info("numeroBotellas: " + numeroBotellas);
		PaqueteEstafetaDto paquete = ventasRep.getPaqueteEstafeta(numeroBotellas);
		
		Double pesoPaquete = 0.00;
		
		
		if (numeroBotellas > 12) {
			pesoPaquete = numeroBotellas * 1.7;
		} else {
			pesoPaquete = paquete.getPeso();
		}
		
		
		LOG.info("------------------- peso Paquete: " + pesoPaquete);
		LOG.info("TIPO ENVIO: " + pago.getTipoEnvio());
		int servicioEnvio = ventasRep.getIdTipoEnvioEstafeta(pago.getTipoEnvio());
		LOG.info("servicioEnvio: " + servicioEnvio);
		ArrayList<ItemDto> items = pago.getItems();
		
		BigDecimal totalCompra = new BigDecimal(pago.getCostoTotal());
		LOG.info("TOTAL COMPRA: " + totalCompra);
		Payer_dto payer = new Payer_dto();
		payer.setFirst_name(usr.getNombre());
		payer.setLast_name(usr.getAppaterno());
		payer.setEmail(usr.getEmail());
		
		PreferenciaPagoDto pref = new PreferenciaPagoDto();
		pref.setPayer(payer);
        pref.setTransaction_amount(totalCompra);
        pref.setPayment_method_id(pago.getTipoTarjeta());
        pref.setToken(pago.getToken());
        pref.setInstallments(Integer.parseInt(pago.getInstallments()));
        
        PayerDto payerAux = new PayerDto();
        payerAux.setFirst_name(usr.getNombre());
        payerAux.setLast_name(usr.getAppaterno());
//        payerAux.setEmail(usr.getEmail());
        
        payerAux.setPhone(new PhoneDto("52", direccion.getNumeroTelefonico()));
        payerAux.setAddress(new AddressDto(pago.getCpTarjeta(), pago.getCalleTarjeta(), pago.getNumCalleTarjera()));
        payerAux.setRegistration_date(usr.getFechaRegistro());
        
        ShipmentsDto shipments = new ShipmentsDto();
        shipments.setReceiver_address(new  AddressDto(direccion.getCodigoPostal(), direccion.getCalle(), direccion.getNumExterior()));
        
        AdditionalInfoDto addInfo = new AdditionalInfoDto();
        addInfo.setItems(items);
        addInfo.setPayer(payerAux);
        addInfo.setShipments(shipments);
        
		pref.setAdditional_info(addInfo);
		
		InfoMailDto mailEnv = new InfoMailDto();

		String auxiliar = "";
        if (!"amex".equals(pago.getTipoTarjeta())) {
        	pref.setIssuer_id(Integer.parseInt(pago.getIssuerID()));
            auxiliar = "    \"issuer_id\": " + pago.getIssuerID() + ",                  \n";
        }
        
        int tokenCarrito = Integer.parseInt(pago.getTokenCarrito());
		
		
        
        
        RespuestaPagoDto respuesta = mercadoPago.procesaPago(pago, pref, usr, paquete, servicioEnvio);
        LOG.info("RESPUESTA: " + respuesta.getStatus());
        LOG.info("TOKEN CARRITO: " + pago.getTokenCarrito());
        boolean approved = respuesta.getStatus().equals("approved") ? true : false;
        LOG.info("BOOLEANO : " + approved);
        if (respuesta.getStatus().equals("approved")) {
        	LOG.info("EMTRA AQUI PARA UNA VEZ APROVADO");
        	VentaDto venta = new VentaDto();
        	venta.setIdUsuario(usr.getIdUsuario());
            venta.setIdDireccion(Integer.parseInt(pago.getIdDireccion()));
            venta.setCostoEnvio(new BigDecimal(pago.getCostoEnvio()));
            venta.setIdEstatusPortal(1);
            venta.setIdMercadoPago(Long.parseLong(respuesta.gettId()));
            venta.setEstatusPago(respuesta.getStatus());
            venta.setEstatusPagoDetalle(respuesta.getStatus_detail());
            venta.setTotal(new BigDecimal(pago.getCostoTotal()));
            venta.setServicioEnvio(servicioEnvio);
            venta.setPesoEnvio(new BigDecimal(pesoPaquete));
        	
            BigDecimal pvpConDesc =  new BigDecimal("0");
        	List<ProductoModel> articulosCarrito = usuarioRepo.getArticulosCarrito(tokenCarrito);
        	int idVenta = ventasRep.registrarVenta(venta);
        	for(ProductoModel p : articulosCarrito) {
        		int insertArt = ventasRep.registrarVentaArticulo(p, idVenta);
        		pvpConDesc.add(p.getPvpConDesc());
        		LOG.info("INSERT: " + insertArt);
        	}
        	
			//SE LLAMA AL SERVICIO DE ENVIO DE CORREO
			mailEnv.setVenta(venta);
			mailEnv.setProductos(articulosCarrito);
			mailEnv.setPref(pref);
			mailEnv.setDireccion(direccion);
			mailEnv.setFecha(usr.getFechaRegistro());
			mailEnv.setNoBotellas(numeroBotellas);
			mailEnv.setPvp(pvpConDesc);
			mailEnv.setEmail(usr.getEmail());

        	boolean envioMail = mail.sendMailConfirmacionCompra(mailEnv);
        	if(envioMail) {
        		LOG.info("SE ENVIO MAIL DE CONFIRAMCION DE COMPRA");
        	} else {
        		LOG.info("NO SE PUDO ENVIAR MAIL DE COMPRA");
        	}
        	
        	List<Integer> ids =  ventasRep.getIdVentasExpontaneas(Integer.parseInt(pago.getTokenCarrito()));
        	LOG.info("VENTAS: " + ids  + " - - - TOKEN carrito: " + pago.getTokenCarrito());
        	for(Integer id : ids) {
        		int borrado = usuarioRepo.deleteArticuloCarrito(Integer.parseInt(pago.getTokenCarrito()), id);
        		LOG.info("BORRADO ART CARRITO: " + borrado);
        	}
        	
        	//Verifica si el usuario esta registrado: 1=REGISTRADO, 0=NO REGISTRADO
        	CustomerResponseDto customerRes = mercadoPago.regresaStatusRegistradoUsuario(usr.getEmail());
        	LOG.info("CUSTUMER-PAGING-TOTAL: " + customerRes.getPaging().getTotal());
        	
			int statusCode = 0;
			MercadoPago.SDK.configure(accessToken);
			Customer customer = new Customer();
        	if (customerRes.getPaging().getTotal() == 0 ) {
        		try {
					LOG.info("Customer mail " + usr.getEmail());
        			
        			customer.setEmail(usr.getEmail());
					customer.save();
					LOG.info("Customer id " + customer.getId());
        			statusCode = mercadoPago.cardsAcces(customer.getId());
        			if (statusCode == 200) {
        				LOG.info("Se registro el usuario "+ customerRes.getResult().getEmail()  +" correctamente en el api de mercado pago");
        			} else {
        				LOG.info("Algo salio mal con el registro del usuario " + customerRes.getResult().getEmail() + " response code: " + statusCode);
        			}
        			
				} catch (MPException e) {
					LOG.error("ERROR:  " + e);
					e.printStackTrace();
				}
        		
        	} else {
        		statusCode = mercadoPago.cardsAcces(customerRes.getResult().getId());
        		if (statusCode == 200) {
    				LOG.info("El usuario "+ customerRes.getResult().getEmail()  +" ya esta registrado en el api de mercado pago");
    			} else {
    				LOG.info("Algo salio mal, usuario " + customerRes.getResult().getEmail() + " response code: " + statusCode);
    			}
			}
			Card card = new Card();
			card.setToken(pago.getToken());
			card.setCustomerId(customerRes.getResult().getId());	
			try {
				card.save();
			} catch (MPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
//        LOG.info("RESPUESTA final%: " + respuesta.toString() );
        return respuesta;

	}

	@Override
	public List<CardDto> getTarjetasAlmacenadas(String mail) {
		List<CardDto> listTarjetas= mercadoPago.obtieneTarjetasMP(mail);
		LOG.info("TARJETAS: " + listTarjetas.toString());
		return listTarjetas;
	}

	@Override
	public List<Integer> getInstallmentsTarjetasAlmacenadas(String firstSix) {
		List<Integer> installments = mercadoPago.getInstallments(firstSix);
		LOG.info("INSTALLMENTS: " + installments.toString());
		return installments;
	}

	@Override
	public RespuestaPagoDto MPTarjetaAlmacenada(CuerpoPagoTarjetaAlmacenadaDto pago) {
		LOG.info("DIRECCION: " + pago.getIdDireccion());
		UsuarioDto usr = ventasRep.getUsuarioPorIdDireccion(Integer.parseInt(pago.getIdDireccion()));
		LOG.info("Usuario: " + usr);
		DireccionUsuarioModel direccion = direccionUsrRep.getDetalleDireccion(Integer.parseInt(pago.getIdDireccion()));
		LOG.info("Direccion: " + direccion.toString());
		int numeroBotellas = ventasRep.getBotellasCarrito(Integer.parseInt(pago.getTokenCarrito()));
		LOG.info("numeroBotellas: " + numeroBotellas);
		PaqueteEstafetaDto paquete = ventasRep.getPaqueteEstafeta(numeroBotellas);
		
		Double pesoPaquete = 0.00;
		
		
		if (numeroBotellas > 12) {
			pesoPaquete = numeroBotellas * 1.7;
		} else {
			pesoPaquete = paquete.getPeso();
		}
		
		
		
		LOG.info(" - - - - - - - - - - - Peso paquete: " + pesoPaquete);
		LOG.info("TIPO ENVIO: " + pago.getTipoEnvio());
		int servicioEnvio = ventasRep.getIdTipoEnvioEstafeta(pago.getTipoEnvio());
		LOG.info("servicioEnvio: " + servicioEnvio);
		
		//int issuer = mercadoPago.validaClienteYTarjeta(pago.getClienteId(), pago.getIdCard());
		List<String> respuestaMP = new ArrayList<String>(mercadoPago.validaClienteYTarjeta(pago.getClienteId(), pago.getIdCard()));
		LOG.info("ISSUER: " + respuestaMP.get(0));
		
		PreferenciaPagoTADto preferencia =  new PreferenciaPagoTADto();
		
		PayerTA payer = new PayerTA();
		payer.setFirst_name(usr.getNombre());
		payer.setLast_name(usr.getAppaterno());
		payer.setEmail(usr.getEmail());
		payer.setType("customer");
        payer.setId(pago.getClienteId());
        
        preferencia.setPayer(payer);
        preferencia.setTransaction_amount(new BigDecimal(pago.getCostoTotal()));
        preferencia.setToken(pago.getToken());
        preferencia.setInstallments(Integer.parseInt(pago.getInstallments()));
        preferencia.setIssuer_id( Integer.parseInt(respuestaMP.get(0)));
        
        AdditionalInfoTADto addInfo = new AdditionalInfoTADto();
        
        PayerDto payerAux = new PayerDto();
        payerAux.setFirst_name(usr.getNombre());
        payerAux.setLast_name(usr.getAppaterno());
        payerAux.setPhone(new PhoneDto("52", direccion.getNumeroTelefonico()));
        payerAux.setAddress(new AddressDto(pago.getCpTarjeta(), pago.getCalleTarjeta(), pago.getNumCalleTarjera()));
        payerAux.setRegistration_date(usr.getFechaRegistro());
        
        ShipmentsDto shipments = new ShipmentsDto();
        shipments.setReceiver_address(new AddressDto(direccion.getCodigoPostal(), direccion.getCalle(), direccion.getNumExterior()));
        
        addInfo.setPayer(payerAux);
        addInfo.setShipments(shipments);
        
		preferencia.setAdditional_info(addInfo);

		Payer_dto payerN = new Payer_dto();
		payerN.setFirst_name(usr.getNombre());
		payerN.setLast_name(usr.getAppaterno());
		payerN.setEmail(usr.getEmail());
		
		PreferenciaPagoDto pref = new PreferenciaPagoDto();
		pref.setPayer(payerN);
        pref.setTransaction_amount(new BigDecimal("1313.86"));
		//pref.setPayment_method_id(pago.getTipoTarjeta());
		pref.setPayment_method_id(respuestaMP.get(1));
        pref.setToken(pago.getToken());
        pref.setInstallments(Integer.parseInt(pago.getInstallments()));
        
        RespuestaPagoDto respuesta = mercadoPago.validaInformacionAdicional(preferencia);
		LOG.info("status respuesta: " + respuesta.getStatus());
        if (respuesta.getStatus().equals("approved") ) {
			VentaDto venta = new VentaDto();
			InfoMailDto mailEnv = new InfoMailDto();
        	venta.setIdUsuario(usr.getIdUsuario());
            venta.setIdDireccion(Integer.parseInt(pago.getIdDireccion()));
            venta.setCostoEnvio(new BigDecimal(pago.getCostoEnvio()));
            venta.setIdEstatusPortal(1);
            venta.setIdMercadoPago(Long.parseLong(respuesta.gettId()));
            venta.setEstatusPago(respuesta.getStatus());
            venta.setEstatusPagoDetalle(respuesta.getStatus_detail());
            venta.setTotal(new BigDecimal(pago.getCostoTotal()));
            venta.setServicioEnvio(servicioEnvio);
//			venta.setPesoEnvio(new BigDecimal(paquete.getPeso()));
			venta.setPesoEnvio(new BigDecimal(pesoPaquete));

			BigDecimal pvpConDesc =  new BigDecimal("0");
        	List<ProductoModel> articulosCarrito = usuarioRepo.getArticulosCarrito(Integer.parseInt(pago.getTokenCarrito()));
        	int idVenta = ventasRep.registrarVenta(venta);
        	for(ProductoModel p : articulosCarrito) {
				int insertArt = ventasRep.registrarVentaArticulo(p, idVenta);
				pvpConDesc.add(p.getPvpConDesc());
        		LOG.info("INSERT: " + insertArt);
        	}
			
			mailEnv.setVenta(venta);
			mailEnv.setProductos(articulosCarrito);
			mailEnv.setPref(pref);
			mailEnv.setDireccion(direccion);
			mailEnv.setFecha(usr.getFechaRegistro());
			mailEnv.setNoBotellas(numeroBotellas);
			mailEnv.setPvp(pvpConDesc);
			mailEnv.setEmail(usr.getEmail());

			boolean envioMail = mail.sendMailConfirmacionCompra(mailEnv);
			
			if(envioMail) {
        		LOG.info("--------\nSE ENVIO MAIL DE CONFIRAMCION DE COMPRA TA");
        	} else {
        		LOG.info("--------\nNO SE PUDO ENVIAR MAIL DE COMPRA TA");
        	}

        	List<Integer> ids =  ventasRep.getIdVentasExpontaneas(Integer.parseInt(pago.getTokenCarrito()));
        	for(Integer id : ids) {
        		int borrado = usuarioRepo.deleteArticuloCarrito(Integer.parseInt(pago.getTokenCarrito()), id);
        		LOG.info("BORRADO ART CARRITO: " + borrado);
        	}
        	
        }
        
        return respuesta;
	}

	@Override
	public List<VentaLDto> obtienePedidos(int idCliente) {
		List<VentaLDto> ventas = ventasRep.getComprasCliente(idCliente);
		return ventas;
	}

	@Override
	public List<DetalleVentaDto> getDetalleVenta(int idVenta) {
		List<DetalleVentaDto> detalle = ventasRep.getDetalleVenta(idVenta);
		return detalle;
	}

}