package application;

import javax.swing.SwingUtilities;
import javax.swing.UIManager; // <<< NOVA IMPORTAÇÃO

public class Main {
    // Inicia o app
    public static void main(String[] args) {
        
        // --- MODIFICAÇÃO PARA TRADUZIR BOTÕES ---
        // Altera o texto padrão dos botões "Yes" e "No" para toda a aplicação.
        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        // --- FIM DA MODIFICAÇÃO ---
        
        // Inicia a interface gráfica
        SwingUtilities.invokeLater(InterfaceLoja::new); //
    }
}