/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package file;
import java.nio.file.Paths; 
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author chung
 */
public class funciones {
    private File file;
    
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
   
