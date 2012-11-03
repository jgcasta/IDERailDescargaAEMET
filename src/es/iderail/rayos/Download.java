package es.iderail.rayos;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;


public class Download {
	
	static Logger logger = Logger.getLogger(Download.class);

    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
    	
    	
    	ConfigLogger.ConfigLogger();
    	
    	logger.info("---------------------- Inicio ----------------------");
    	
    	int resultFTP = 0;
    	String patronDir = "";
    	String dirLocal = LeerPropiedades.leeProp("dirLocal"); 

    	Calendar calendar = Calendar.getInstance();

  	    int dia = calendar.get(Calendar.DAY_OF_MONTH);  
  	    String strDia = "";
  	    if (dia < 10){
  	    	strDia = "0" + Integer.toString(dia);
  	    }else{
  	    	strDia = Integer.toString(dia);
  	    }
	    int mes = calendar.get(Calendar.MONTH) + 1;  
	    String strMes = "";
	    if (mes <10){
	    	strMes = "0" + Integer.toString(mes);
	    }else{
	    	strMes = Integer.toString(mes);
	    }
	    int anio = calendar.get(Calendar.YEAR);  //año
	 

	    patronDir = Integer.toString(anio) + strMes + strDia ;
	    
	    
	    String dirFTP = "rayos/" + patronDir + "/";
    	String fich = "";
    	
    	
    	fich = listFiles.UltimoFichero(dirFTP); 
    	
    	logger.info("Descargando el fichero " + dirFTP + fich);
    	resultFTP = DownloadFile("ftpdatos.aemet.es", "anonymous", "jgcasta@gmail.com", dirFTP + fich, dirLocal + fich);
    	
    	
    	logger.info("Descomprimiendo" + dirLocal + fich);
    	if (resultFTP == 0){
    		Unzip(dirLocal + fich);
    	}
    	
    	
    	logger.info("Parseando a la BD");
    	MySQL.parsear(dirLocal + "datosRayosDescargados.txt");
    	
    	File archivo = new File(dirLocal + "datosRayosDescargados.txt");
    	archivo.delete();
    	archivo = null;
    	logger.info("---------------------- Terminando descarga y parseado----------------------");
    	
    	
    }
    
    /**
     * Descarga un fichero del servidor FTP
     * 
     * @param ftpserver <b>String</b> IP address of the ftp server
     * @param username <b>String</b> Username
     * @param passwd <b>String</b> Password
     * @param sourcefile <b>String</b> Filename and path of the source remote file with path 
     * @param destinationfile <b>String</b> Filename and path of the local destination file with path
     * @return <b>int</b> 	0 - correct
	 * 						1 - cannot download file
	 * 						2 - cannot disconnect
     */
    public static int DownloadFile(String ftpserver, String username,
			String passwd,String sourcefile,String destinationfile){
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;
        int out = 0;
        
        try {
            client.connect(ftpserver);
            client.login(username, passwd);
            
        
            client.setFileType(FTP.BINARY_FILE_TYPE);

        
            String filename = destinationfile;
            fos = new FileOutputStream(filename);

        
            client.retrieveFile( sourcefile, fos);
        
        } catch (IOException e) {
        	e.printStackTrace();
            logger.error("Error descargando archivo " + sourcefile);
            logger.error(e.toString());
            out = 1;
        } catch (Exception e2){
        	logger.error("Error descargando archivo " + sourcefile);
            logger.error(e2.toString());
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                out = 2;
            }
        }
        return out;

    }
    
    /**
     * 
     * Descomprime un fichero en formato gz y una vez descomprimido lo borra
     * 
     * @param fich <b>String</b> Nombre el fichero a descomprimir
     * @return <b>int</b>	0 - descompresión correcta
     * 						1- no se ha podido descomprimir
     */
    public static int Unzip(String fich){
    	
    	int resultado = 0; 
    	boolean hecho = true;
    	
        try{
            
        	String inFilename = fich;
        	String outFilename = LeerPropiedades.leeProp("dirLocal") + "/datosRayosDescargados.txt";
        	
            GZIPInputStream gzipInputStream = null;
            
            gzipInputStream = new GZIPInputStream(new FileInputStream(inFilename));
            OutputStream out = new FileOutputStream(outFilename);

            byte[] buf = new byte[1024];  
            int len;
            while ((len = gzipInputStream.read(buf)) > 0) {
              out.write(buf, 0, len);
            }

            gzipInputStream.close();
            out.close();
            
            // borro el fichero comprimido
            hecho = DeleteFile(fich);

        }
        catch(IOException e){
          logger.error("Error al descomprimimr el fichero " + fich);
          logger.error(e.toString());
          resultado = 1;
        }
        
        return resultado;
      }    

    /**
     * Borrado de un fichero
     * 
     * @param fich
     * @return
     */
    public static boolean DeleteFile(String fich){
    	File fichero = new File(fich);
  	
    	return fichero.delete();
    	

    }
}