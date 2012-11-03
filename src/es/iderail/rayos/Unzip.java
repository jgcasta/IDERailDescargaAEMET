package es.iderail.rayos;

import java.util.zip.GZIPInputStream;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Unzip{
  public static void Unzip(String fich){
    try{
      //To Uncompress GZip File Contents we need to open the gzip file.....
        
        
    	String inFilename = fich;
    	
        GZIPInputStream gzipInputStream = null;
        FileInputStream fileInputStream = null;
        
        gzipInputStream = new GZIPInputStream(new FileInputStream(inFilename));
        String outFilename = "/home/pepe/IDE/AEMET/datos/ultimosDatos.csv";
        OutputStream out = new FileOutputStream(outFilename);

        byte[] buf = new byte[1024];  //size can be changed according to programmer's need.
        int len;
        while ((len = gzipInputStream.read(buf)) > 0) {
          out.write(buf, 0, len);
        }

        gzipInputStream.close();
        out.close();

    }
    catch(IOException e){
      System.out.println("Exception has been thrown" + e);
    }
  }
} 
