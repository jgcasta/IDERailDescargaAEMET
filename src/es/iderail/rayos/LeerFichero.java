package es.iderail.rayos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.log4j.Logger;

public class LeerFichero {
	
	static Logger logger = Logger.getLogger(LeerFichero.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String ruta = "";
		
		leerFichero(ruta);
	}
	
	/**
	 * Lectura de un fichero
	 * 
	 * @param path <b>String</b> Path del ficero a leer 
	 * 
	 */
	public static void leerFichero(String path){
		
		ConfigLogger.ConfigLogger();
		
		String ruta = path;
		File fich = null;
		FileReader fr = null;
		BufferedReader br = null;
		
		try{
			
			fich = new File(ruta);
			
			fr = new FileReader(fich);
			br = new BufferedReader(fr);
			
			String linea = "";
			while ((linea = br.readLine()) != null){
				
				String[] campo = linea.split(",");
				
				System.out.println(campo[0]);
			}
		
		}catch(Exception e){
			logger.error("No puedo leer el fichero " + fich);
			e.printStackTrace();
			
		}finally{
			
			try{
				if (fr != null){
					fr.close();
				}
			}catch(Exception e2){
				logger.equals("No puedo cerrar el fichero " + fich);
				e2.printStackTrace();
			}
		
		}
		

	}

}
