package file;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class archivos {
    
     private File mifile = null;
     private Date fechaActual;

    public archivos() {
        fechaActual = new Date();
    }
     
     
    void setFile(String direccion) {
        mifile = new java.io.File(direccion);
    }
    
    boolean mfile() throws IOException{
    return mifile.createNewFile();
    }
    
    boolean mkdir(){
    return mifile.mkdirs();
    }   
    
    public String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaActual);
    }

    public String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(fechaActual);
    }
    
    public void puntos(){
        

}
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
