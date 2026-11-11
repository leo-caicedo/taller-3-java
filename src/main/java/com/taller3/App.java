package com.taller3;

import com.taller3.ui.VentanaPrincipal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Punto de entrada de la aplicación.
 */
public final class App {

    private App() {
        // Clase de arranque: no se instancia.
    }

    public static void main(String[] args) {
        configurarApariencia();
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }

    /**
     * Intenta usar la apariencia Nimbus, disponible en todas las distribuciones de Java.
     * Si no está disponible se conserva la apariencia del sistema.
     */
    private static void configurarApariencia() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    return;
                }
            }
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            // Si no se puede cambiar la apariencia se usa la predeterminada de Java.
        }
    }
}
