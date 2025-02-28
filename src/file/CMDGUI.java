package file;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author danilos
 */
public class CMDGUI extends JFrame implements ActionListener {
    
    private JPanel Fondo;
    private JLabel DIR;
    private JTextField dirTextField;
    private JTextArea outputArea;
    private ArrayList<String> direcciones = new ArrayList<>();
    private archivos fileManager;
    private String currentPath = "C:/Users/";
    private boolean modoEscritura = false;
    private String archivoEscritura = "";

    public CMDGUI(){
        fileManager = new archivos();
        fileManager.setFile(currentPath);
        
        this.setTitle("Simbolo de Sistema");
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        Fondo = new JPanel();
        Fondo.setBackground(Color.BLACK);
        Fondo.setLayout(null);
        
        DIR = new JLabel("C:/Users/>");
        DIR.setBounds(10, 10, 90, 30);
        DIR.setForeground(Color.WHITE);
        Fondo.add(DIR);
        
        dirTextField = new JTextField();
        dirTextField.setBounds(100, 10, 710, 30);
        dirTextField.setBackground(Color.BLACK);
        dirTextField.setForeground(Color.WHITE);
        dirTextField.setCaretColor(Color.WHITE);
        dirTextField.setBorder(null); 
        dirTextField.addActionListener(this); 
        Fondo.add(dirTextField);
        
        outputArea = new JTextArea();
        outputArea.setBounds(10, 50, 1160, 500);
        outputArea.setBackground(Color.BLACK);
        outputArea.setForeground(Color.WHITE);
        outputArea.setEditable(false);
        outputArea.setFont(DIR.getFont());
        Fondo.add(outputArea);
        
        this.setContentPane(Fondo);
        this.setVisible(true);
    }
   
  @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == dirTextField) {
        String command = dirTextField.getText().trim();

        if (modoEscritura) {
            if (command.equalsIgnoreCase("salir")) {
                modoEscritura = false;
                archivoEscritura = "";
                outputArea.append("Modo de escritura desactivado.\n");
            } else {
                fileManager.setFile(archivoEscritura);
                String resultado = fileManager.Escribir(command + "\n");

                if (resultado == null) {
                    outputArea.append("Texto agregado: " + command + "\n");
                } else {
                    outputArea.append("Error al escribir en el archivo: " + resultado + "\n");
                }
            }

            dirTextField.setText("");
            return; 
        }

        if (!command.isEmpty()) {
            outputArea.append(currentPath + "> " + command + "\n");

            try {
                processCommand(command);
            } catch (Exception ex) {
                outputArea.append("Error: " + ex.getMessage() + "\n");
            }

            dirTextField.setText("");
        }
    }
}


    
    private void processCommand(String command) throws IOException {
        String[] parts = command.split(" ", 2);
        String cmd = parts[0].toLowerCase();
        String arg = (parts.length > 1) ? parts[1] : "";
        
        fileManager.setFile(currentPath);
        
        switch (cmd) {
            case "mkdir":
                if (!arg.isEmpty()) {
                    String newDirPath = currentPath + arg;
                    fileManager.setFile(newDirPath);
                    if (fileManager.mkdir()) {
                        outputArea.append("Directorio creado: " + arg + "\n");
                    } else {
                        outputArea.append("No se pudo crear el directorio: " + arg + "\n");
                    }
                } else {
                    outputArea.append("Error: Se requiere un nombre para el directorio\n");
                }
                break;
               case "mfile":
            
                if (!arg.isEmpty()) {
                    String newFilePath = currentPath + arg;
                    fileManager.setFile(newFilePath);
                    try {
                        if (fileManager.mfile()) {
                            outputArea.append("Archivo creado: " + arg + "\n");
                        } else {
                            outputArea.append("No se pudo crear el archivo: " + arg + " (puede que ya exista)\n");
                        }
                    } catch (IOException ex) {
                        outputArea.append("Error al crear el archivo: " + ex.getMessage() + "\n");
                    }
                } else {
                    outputArea.append("Error: Se requiere un nombre para el archivo\n");
                }
                break;
          case "cd":
    if (arg.isEmpty() || arg.equals("..")) {
        File currentDir = new File(currentPath);
        File parentDir = currentDir.getParentFile();
        if (parentDir != null) {
            currentPath = parentDir.getAbsolutePath() + "/";
            updateDIRLabel();
            outputArea.append("Directorio actual: " + currentPath + "\n");
            direcciones.add(currentPath);
        } else {
            outputArea.append("Ya está en el directorio raíz\n");
        }
    } else {
        String newPath;
        if (arg.startsWith("/") || arg.contains(":")) {
            newPath = arg;
        } else {
            newPath = currentPath + arg;
        }
        
        File dir = new File(newPath);
        if (dir.exists() && dir.isDirectory()) {
            currentPath = dir.getAbsolutePath() + "/";
            currentPath = currentPath.replace("\\", "/");
            updateDIRLabel();
            outputArea.append("Directorio actual: " + currentPath + "\n");
            direcciones.add(currentPath); 
        } else {
            outputArea.append("El directorio no existe: " + arg + "\n");
        }
    }
    break;

                
            case "dir":
                fileManager.setFile(currentPath);
                StringBuilder dirContents = fileManager.Dir();
                outputArea.append(dirContents.toString() + "\n");
                break;
                
            case "date":
                outputArea.append("Fecha actual: " + fileManager.date() + "\n");
                break;
                
            case "time":
                outputArea.append("Hora actual: " + fileManager.time() + "\n");
                break;
                
           case "rm":
    if (!arg.isEmpty()) {
        File fileToDelete = new File(currentPath + arg);
        if (fileToDelete.exists()) {
            if (fileManager.Rm(fileToDelete)) {
                outputArea.append("Eliminado: " + arg + "\n");
            } else {
                outputArea.append("No se pudo eliminar: " + arg + " (puede estar en uso o sin permisos)\n");
            }
        } else {
            outputArea.append("El archivo o directorio no existe: " + arg + "\n");
        }
    } else {
        outputArea.append("Error: Se requiere especificar un archivo o directorio\n");
    }
    break;

           case "...":
    if (!direcciones.isEmpty()) {
        direcciones.remove(direcciones.size() - 1);
        if (!direcciones.isEmpty()) {
            currentPath = direcciones.get(direcciones.size() - 1); 
            updateDIRLabel();
            outputArea.append("Retrocediendo a: " + currentPath + "\n");
        } else {
            outputArea.append("No hay más directorios en el historial.\n");
        }
    } else {
        outputArea.append("No se pudo retroceder en el historial de directorios\n");
    }
    break;

     case "escribir":
    if (!arg.isEmpty()) {
        String filePath = currentPath + arg;
        File file = new File(filePath);

        if (!file.exists()) {
            outputArea.append("El archivo no existe. Usa 'mfile' para crearlo primero.\n");
        } else {
            archivoEscritura = filePath;
            modoEscritura = true;
            outputArea.append("Modo de escritura activado para: " + arg + "\n");
            outputArea.append("Escribe el contenido y presiona Enter. Usa 'salir' para finalizar.\n");
        }
    } else {
        outputArea.append("Error: Se requiere un nombre de archivo.\n");
    }
    break;

    case "salir":
    if (modoEscritura) {
        modoEscritura = false;
        archivoEscritura = "";
        outputArea.append("Modo de escritura desactivado.\n");
    } else {
        outputArea.append("Comando no reconocido: " + cmd + "\n");
    }
    break;   
            case "leer":
                if (!arg.isEmpty()) {
                    String filePath = currentPath + arg;
                    fileManager.setFile(filePath);
                    try {
                        StringBuilder content = fileManager.Leer();
                        if (content.length() > 0) {
                            outputArea.append("Contenido de " + arg + ":\n");
                            outputArea.append(content.toString() + "\n");
                        } else {
                            outputArea.append("El archivo está vacío o no se puede leer\n");
                        }
                    } catch (FileNotFoundException ex) {
                        outputArea.append("Archivo no encontrado: " + arg + "\n");
                    }
                } else{
                }
                break;
                
            default:
                outputArea.append("Comando no reconocido: " + cmd + "\n");
                break;
        }
    }
    
    private void updateDIRLabel() {
        DIR.setText(currentPath + ">");
    }

   
}
