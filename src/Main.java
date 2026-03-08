import gui.MainMenuFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Ejecutar la Interfaz Gráfica en el hilo seguro de Swing
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame ventanaPrincipal = new MainMenuFrame();
            ventanaPrincipal.setVisible(true);
        });
    }
}