package file;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author danilos
 */
public class CMDGUI extends JFrame implements ActionListener {
    
    private JPanel Fondo;
    private JLabel DIR;
    private JTextField dirTextField;
    private JTextArea outputArea;
    
    public static void main(String[] args) {
        new CMDGUI();
    }
    
    public CMDGUI(){
        this.setTitle("Simbolo de Sistema");
        this.setSize(1200, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        Fondo = new JPanel();
        Fondo.setBackground(Color.BLACK);
        Fondo.setLayout(null);
        
        // DIR label
        DIR = new JLabel("C:/Users/>");
        DIR.setBounds(10, 10, 90, 30);
        DIR.setForeground(Color.WHITE);
        Fondo.add(DIR);
        
        dirTextField = new JTextField();
        dirTextField.setBounds(100, 10, 710, 30);
        dirTextField.setBackground(Color.BLACK);
        dirTextField.setForeground(Color.WHITE);
        dirTextField.setCaretColor(Color.WHITE);
        dirTextField.setBorder(null); // Remove border
        dirTextField.addActionListener(this); 
        Fondo.add(dirTextField);
        
        // Add output text area for displaying command history
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
            String command = dirTextField.getText();
            if (command != null && !command.isEmpty()) {
                outputArea.append("C:/Users/> " + command + "\n");
                
                dirTextField.setText("");
            }
        }
    }
}