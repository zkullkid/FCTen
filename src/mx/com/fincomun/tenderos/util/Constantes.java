package mx.com.fincomun.tenderos.util;

import java.util.Properties;

public class Constantes {

	public final static String ARCHIVO_PROPERTIES = "./fctenderosws/conf/tenderosws.properties";
	public static Properties properties = TenderosProperties.cargaConfiguracion(ARCHIVO_PROPERTIES);
	
	public static final String PASSWORD_KEY = properties.getProperty("password.key");
    public static final String PASSWORD_IV = properties.getProperty("password.iv");
    
    public final static String GENERA_SERVICE_TOKEN = properties.getProperty("genera.token.security");
    public final static String VALIDA_SERVICE_TOKEN = properties.getProperty("valida.token.security");
    
	public static final String CODIGO_ERROR = "-3";
	public static final String CODIGO_ERROR_OPERACION = "-2";
	public static final String CODIGO_ERROR_REQUEST = "-1";
	public static final String CODIGO_EXITO = "0";
	
	public static final String MSJ_ERROR_PROVEEDOR_EXISTE = "El proveedor ya existe.[Nombre o Correo]";
	public static final String MSJ_ERROR_NO_EXISTE_PROVEEDOR = "El proveedor no existe.";
	public static final String MSJ_ERROR_NO_EXISTEN_PROVEEDORES = "No se obtuvieron proveedores.";
	public static final String MSJ_ERROR_PRODUCTO_EXISTE = "El producto ya existe.";
	public static final String MSJ_ERROR_PRODUCTO_NO_EXISTE = "El producto no existe.";
	public static final String MSJ_ERROR_NO_EXISTEN_PRODUCTOS = "No se obtuvieron art\u00edculos.";
	public static final String MSJ_ERROR_NO_EXISTEN_TIPOS_PAGO = "No se obtuvieron tipos de pago.";
	public static final String MSJ_ERROR_NO_EXISTEN_TIPO_PAGO = "El tipo de pago no existe";
	public static final String MSJ_ERROR_NO_EXISTEN_DATOS = "No se obtuvieron resultados";
	
	public static final String MSJ_ERROR_STOCK = "No existen productos suficientes para la venta. Verifica tu inventario.";
	public static final String MSJ_ERROR_TOKEN = "El token no es valido";
	public static final String MSJ_ERROR = "Ocurrio un error al realizar la operaci\u00f3n.";
	public static final String MSJ_EXITO = "Operaci\u00f3n Exitosa.";
	
	public static final String MSJ_COMPRA_DIRECTA = "Compra Directa";
	
	public static final int BUSCAR_ARTICULOS_POR_NOMBRE_ASC = 1;
	public static final int BUSCAR_ARTICULOS_POR_NOMBRE_DESC = 2;
	public static final int BUSCAR_ARTICULOS_POR_PRECIO_VENTA_ASC = 3;
	public static final int BUSCAR_ARTICULOS_POR_PRECIO_VENTA_DESC = 4;
	public static final int BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_ASC = 5;
	public static final int BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_DESC = 6;
	
	public static final String BUSCAR_ARTICULOS_POR_NOMBRE_ASC_BD = " TA.NOMBRE ASC ";
	public static final String BUSCAR_ARTICULOS_POR_NOMBRE_DESC_BD = " TA.NOMBRE DESC ";
	public static final String BUSCAR_ARTICULOS_POR_PRECIO_VENTA_ASC_BD = " TA.PRECIO_VENTA ASC ";
	public static final String BUSCAR_ARTICULOS_POR_PRECIO_VENTA_DESC_BD = " TA.PRECIO_VENTA DESC ";
	public static final String BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_ASC_BD = " TA.PRECIO_COSTO ASC ";
	public static final String BUSCAR_ARTICULOS_POR_PRECIO_COMPRA_DESC_BD = " TA.PRECIO_COSTO DESC ";
	
	public static final String TIPO_PAGO_CODI = "CoDi";
	public static final String TIPO_PAGO_EFECTIVO = "Efectivo";
	public static final String TIPO_PAGO_TARJETA = "Tarjeta";
	
        public static final String listaServers=properties.getProperty("lista.servidores");
        
        public interface SMS{
            public static final String REGION_MX = "52";
            public static final String URL_REQUEST_MX = "https://app.smsmasivos.com.mx/components/api/api.envio.sms.php";
            public static final String API_KEY_MX = "2612d30f3dd98a206ccd2f8771adf99949ae2384";
        }
}
