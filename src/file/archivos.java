package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class archivos {
     private File file = null;
     private Date fechaActual;

    public archivos() {
        fechaActual = new Date();
    }
     
     
    void setFile(String direccion) {
        file = new java.io.File(direccion);
    }
    
    boolean mfile() throws IOException{
    return file.createNewFile();
    }
    
    boolean mkdir(){
    return file.mkdirs();
    }   
    
    public String date() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(fechaActual);
    }

    public String time() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(fechaActual);
    }
    
   public boolean puntos(ArrayList<String> array) {
    if (!array.isEmpty()) {
        String direccion = array.get(array.size() - 1);
        File tempFile = new File(direccion);
        if (tempFile.exists() && tempFile.isDirectory()) {  
            Cd(direccion);
            return true;
        }
    }
    return false;
   }

    
    
    public boolean Rm( File file){
        if(file.isDirectory()){
            for(File hijos : file.listFiles()){
                Rm(hijos);
            }
        
        }
        return file.delete();
    }
      
    public void Cd( String direccion ){
        file = new File(direccion);
    }
    
    public StringBuilder Dir(){
        StringBuilder dir = new StringBuilder("");
        if(file.isDirectory()){
            dir.append("Folder: ").append(file.getName()).append("\n");
            int dirs=0, files=0,bytes=0;
            
            if(file.listFiles()!=null){
            for(File child: file.listFiles()){
                System.out.print(new Date(child.lastModified()));
                if(child.isDirectory()){
                    dir.append("\t<DIR>\t");
                    dirs++;
                }
                if(child.isFile()){
                    dir.append("\t    \t");
                    dir.append(child.length());
                    files++;
                    bytes+=child.length();
                }
                dir.append("\t").append(child.getName()).append("\n");
            }}
            dir.append("{").append(files).append(") files y {").append(dirs).append(") dirs");
            dir.append("bytes: ").append(bytes).append("\n");
        
        }
        else{
            dir.append("Accion no permitida");
        }
        return dir;
    }
    
    public String Escribir(String contenido){
        if(file.isDirectory()){
        try{
        Path path = Paths.get(file.getPath());
        Files.writeString( path, contenido, StandardOpenOption.APPEND);
        return null;
        
        }catch(IOException e){
        return "Error" + e.getMessage();
        }}
        return "No es un archivo";
    }
    
    public StringBuilder Leer() throws FileNotFoundException {
    StringBuilder texto = new StringBuilder();
        if (file.canRead()) {
            try (Scanner lector = new Scanner(file)) {  
                while (lector.hasNextLine()) {
                    texto.append(lector.nextLine()).append("\n"); 
                }
            }
        }
    return texto;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
