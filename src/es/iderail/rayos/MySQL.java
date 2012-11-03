package es.iderail.rayos;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;


public class MySQL {

	static Logger logger = Logger.getLogger(MySQL.class);
	
	static Connection con = null;
	
	
	/**
	 * @param args
	 * @throws  
	 */
	public static void main(String[] args) {
		ConfigLogger.ConfigLogger();
		String ficheroParsear =  LeerPropiedades.leeProp("dirLocal") + "201209091800_descargas.txt";
		parsear(ficheroParsear);

	}
	
	public static void parsear(String ficheroParsear ){

		ConfigLogger.ConfigLogger();
		
		conexion();
		String ruta = ficheroParsear;
		
		BufferedReader br = null;
		FileInputStream is = null;
		String linea = "";
		String sql = "";
		String[] campos = null;
		
		try {
			is = new java.io.FileInputStream(ruta);
			br = new BufferedReader(new InputStreamReader(is,"ISO-8859-1"));

			Statement st = con.createStatement();
			int contador = 0;
			
			while (linea != null) {
			  
			  contador ++;

			  linea = br.readLine();
			  if (linea != null){
				  //System.out.println(linea);
				  if (contador > 1) {
		
					  linea = linea.replaceAll(" ", "");
					  campos = linea.split("\t");
					  
					  String fecha = campos[0] + "-" + campos[1].trim()+ "-" + campos[2].trim() + " " + campos[3].trim() + ":" + campos[4].trim()+ ":" + campos[5].trim();
					  String lat = campos[6];
					  String lon = campos[7];
					  String str = campos[8];
					  String nse = campos[9];
					  				  
		  			  sql = "INSERT INTO rayos (fecha, lat, lon, str, nse) values('" + fecha + "','" + lat + "','" + lon + "','" + str + "','" + nse + "')" ;
					  //System.out.println(sql);			
					  st = con.createStatement();
						
					  st.executeUpdate(sql);
					  
				  }
			  }

			}
			br.close();
			cerrar(con);
			
		} catch (FileNotFoundException e1) {
			logger.error("No encuentro del fichero " + ruta);
			logger.error(e1.toString());
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
		} catch (SQLException e) {
			logger.error("Error SQL ");
			logger.error(e.toString());
		}catch (Exception efinal){
			logger.error("Error general " + efinal.toString());
		}
		
		
		
	}
	
	public static void MySQL(){
		
	}
	
	public static Connection conexion() {
		
		if (con == null){
			String usuario = LeerPropiedades.leeProp("usuarioMySQL");
			String clave = LeerPropiedades.leeProp("claveMySQL");
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/meteo",usuario,clave);
			} catch (ClassNotFoundException e) {

				logger.error("No encuentro del driver de conexion " + e.toString());
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return con;
	}

	public static void cerrar(Connection con){
		if (con != null){
			try {
				con.close();
			} catch (SQLException e) {
				logger.error("No puedo cerrar la conexion " + e.toString());
				e.printStackTrace();
			}
		}
	}


}
