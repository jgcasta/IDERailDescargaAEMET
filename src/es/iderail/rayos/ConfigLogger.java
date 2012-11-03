package es.iderail.rayos;


import org.apache.log4j.PropertyConfigurator;


/**
 * Configuración del log4j
 * 
 * @author pepe
 *
 */
public class ConfigLogger {

	public static void ConfigLogger(){
		
		PropertyConfigurator.configure("/var/www/vhosts/astroide.es/httpdocs/trenes/scripts/log4j.properties");
		
	}
}
