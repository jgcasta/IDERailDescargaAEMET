package es.iderail.rayos;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LeerPropiedades {


	public static void main(String[] args) {
		System.out.println(LeerPropiedades.leeProp("dirLocal"));
	}

	public static String leeProp(String prop){
		String propiedad = "";
		Properties props = new Properties();
        FileInputStream file;
        String pathFichero = "aemet.properties";
		try {
			file = new FileInputStream(pathFichero);
			
			props.load(file);
	        propiedad = props.getProperty(prop);
	        file.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}catch (IOException e) {

			e.printStackTrace();
		}
		return propiedad;
	}
}
