package es.iderail.rayos;



import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import java.io.IOException;

public class deleteFile {
    public static void main(String[] args) {
    	int result = 0;

    }
    
    /**
     * 
     * @param ftpserver <b>String</b> IP address of the ftp server
     * @param username <b>String</b> Username
     * @param passwd <b>String</b> Password
     * @param sourcefile <b>String</b> Filename and path of the file will be delete
     * @return <b>int</b> 	0 - correct
	 * 						1 - cannot delete the file
	 * 						2 - file not exists
	 * 						3 - cannot disconnect
     */
    public static int DeleteFile(String ftpserver, String username,
			String passwd,String sourcefile){
        FTPClient client = new FTPClient();
        boolean resultDelete = false;
        int out = 0;
        
        try {
            client.connect(ftpserver);
            client.login(username, passwd);
            
            // connection must be binary
            client.setFileType(FTP.BINARY_FILE_TYPE);

            // delete the file
            resultDelete = client.deleteFile(sourcefile);
            if (!resultDelete) {
                out = 1; // cannot delete the file
            }

            client.logout();
        } catch (IOException e) {
            e.printStackTrace();
            out = 2;
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                out = 3;
            }
        }
        return out;
    }
}