package application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
  
    public static void main(String[] args) {
    
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
     
        SwingUtilities.invokeLater(InterfaceLoja::new); 
    }
}