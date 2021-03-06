package mx.com.fincomun.tenderos.controller;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import mx.com.fincomun.tenderos.bean.CompraDirectaRequest;
import mx.com.fincomun.tenderos.bean.CompraRequest;
import mx.com.fincomun.tenderos.bean.Response;
import mx.com.fincomun.tenderos.bean.TipoPagoResponse;
import mx.com.fincomun.tenderos.bean.ValidaTokenRespose;
import mx.com.fincomun.tenderos.service.ComprasService;
import mx.com.fincomun.tenderos.service.ValidaTokenService;
import mx.com.fincomun.tenderos.util.Constantes;
import mx.com.fincomun.tenderos.util.StringEncrypt;
import mx.com.fincomun.tenderos.util.Util;

@Controller
public class ComprasController {

	private static Logger log = Logger.getLogger(ComprasController.class);
	
	@Autowired
	private ComprasService comprasService;
	
	@Autowired
	private ValidaTokenService validaTokenService;

	@RequestMapping(value = "/restful/compra/articulo", method = RequestMethod.POST)
	public @ResponseBody String compraArticulo(@RequestBody String requestString) {
		Response response = new Response();
		log.info("ServicioRest [/restful/compra/articulo] .... ");
		
		CompraRequest request = converJsonToCompra(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null) { 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestCompra(request);
				if(error == null){
					int isAltaCompra;
					try {
						isAltaCompra = comprasService.altaCompra(request);
						if(isAltaCompra == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isAltaCompra == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_TIPO_PAGO);
						} else if(isAltaCompra == -2){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTE_PROVEEDOR);
						} else if(isAltaCompra == -3){
							response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
							response.setMsjError(Constantes.MSJ_ERROR);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new Response();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}		
			} else {
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}	
		} else {
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	@RequestMapping(value = "/restful/compra/directa", method = RequestMethod.POST)
	public @ResponseBody String compraDirecta(@RequestBody String requestString) {
		Response response = new Response();
		log.info("ServicioRest [/restful/compra/directa] .... ");
		
		CompraDirectaRequest request = converJsonToCompraDirecta(requestString);
		
		ValidaTokenRespose validaTokenResponse = validaTokenService.validaToken(request.getTokenJwt());
		
		if(validaTokenResponse != null){ 
			if(validaTokenResponse.getCodigo() == 0){
				String error = validateRequestCompraDirecta(request);
				if(error == null){
					int isAltaCompra;
					try {
						isAltaCompra = comprasService.altaCompraDirecta(request);
						if(isAltaCompra == 0){
							response.setCodError(Constantes.CODIGO_EXITO);
							response.setMsjError(Constantes.MSJ_EXITO);
						} else if(isAltaCompra == -1){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTEN_TIPO_PAGO);
						}else if(isAltaCompra == -2){
							response.setCodError(Constantes.CODIGO_ERROR);
							response.setMsjError(Constantes.MSJ_ERROR_NO_EXISTE_PROVEEDOR);
						} else if(isAltaCompra == -3){
							response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
							response.setMsjError(Constantes.MSJ_ERROR);
						}
					} catch (Exception e) {
						log.error("Error: "+e.getCause());
						response = new Response();
						response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
						response.setMsjError(Constantes.MSJ_ERROR);
					}
				} else {
					response.setCodError(Constantes.CODIGO_ERROR_REQUEST);
					response.setMsjError(error);
				}
			} else {
				response.setCodError(String.valueOf(validaTokenResponse.getCodigo()));
				response.setMsjError(validaTokenResponse.getMensaje());
			}
		} else {
			response.setCodError(Constantes.CODIGO_ERROR_OPERACION);
			response.setMsjError(Constantes.MSJ_ERROR_TOKEN);
		}
		return Util.getResponse(response);
	}
	
	private String validateRequestCompraDirecta(CompraDirectaRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request compra: [ "+request.toString()+" ]");
			if(request.getMontoTotal() == 0){
				error = "Monto total es 0";
			} else if(request.getIdTipoPago() == 0){
				error = "IdTipoPago es 0";
			} else if(request.getIdUsuario() ==  0){
				error = "IdUsuario es 0";
			} else if(request.getIdProveedor() == 0){
				error = "IdProveedor es 0";
			}
		} 
		return error;
	}
	
	private String validateRequestCompra(CompraRequest request){
		String error = null;
		if(request != null){
			log.info("Validando Request compra: [ "+request.toString()+" ]");
			if(request.getMontoTotal() == 0){
				error = "Monto total es 0";
			} else if(request.getIdTipoPago() == 0){
				error = "IdTipoPago es 0";
			} else if(request.getIdUsuario() == 0){
				error = "IdUsuario es 0";
			} else if(request.getIdProveedor() == 0){
				error = "IdProveedor es 0";
			} else if(request.getListaProductos() == null || request.getListaProductos().size() == 0){
				error = "La lista de los productos es vacia";
			} 
		} 
		return error;
	}

	private CompraRequest converJsonToCompra(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		CompraRequest compraRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			compraRequest = mapper.readValue(request, CompraRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return compraRequest;
	}
	
	private CompraDirectaRequest converJsonToCompraDirecta(String requestEncrypt){
		ObjectMapper mapper = new ObjectMapper();
		CompraDirectaRequest compraDirectaRequest = null;
		try {
			log.info("Request String: ["+requestEncrypt+"]");
			String request = StringEncrypt.decrypt(Constantes.PASSWORD_KEY, Constantes.PASSWORD_IV,requestEncrypt);
			compraDirectaRequest = mapper.readValue(request, CompraDirectaRequest.class);
		} catch (IOException e) {
			log.error("Error al convertir Object: "+e.getMessage());
		} catch (Exception e) {
			log.error("Error al desencriptar Object: "+e.getMessage());
		}
		return compraDirectaRequest;
	}
}
