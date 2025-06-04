package application;

import javax.swing.SwingUtilities;

public class Main {
    // Inicia o app
    public static void main(String[] args) {
        SwingUtilities.invokeLater(InterfaceLoja::new);
    }
}

