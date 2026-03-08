package gui;

import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana principal del sistema.
 * Es un JFrame (Ventana de Swing) que sirve como hub o menú principal.
 * Desde aquí, el usuario podrá navegar a los diferentes formularios.
 */
public class MainMenuFrame extends JFrame {

    // Instancia principal de la lógica del sistema.
    // Todas las subventanas necesitarán esta instancia para leer/guardar datos.
    private Agencia agencia;

    public MainMenuFrame() {
        // Inicializamos la agencia con un límite inicial de 100 para todas las listas.
        this.agencia = new Agencia(100, 100, 100);

        // Simulación: Se cargarían los datos si existieran
        this.agencia.cargarDatos();

        // Configuración de la ventana principal
        setTitle("Sistema de Gestión - Agencia de Modelaje");
        setSize(500, 400); // Ancho x Alto
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar app al cerrar ventana
        setLocationRelativeTo(null); // Centrar en pantalla

        iniciarComponentes();
    }

    /**
     * Método para crear e inicializar todos los componentes visuales (botones,
     * paneles).
     * Esto hace que el código sea Modular y fácil de entender.
     */
    private void iniciarComponentes() {
        // Usaremos un JPanel principal con un BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("Menú Principal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel de Botones Centrales ---
        // GridLayout(6, 1, 15, 15) significa: 6 filas, 1 columna, 15px espacio
        // horizontal, 15px vertical
        JPanel panelBotones = new JPanel(new GridLayout(6, 1, 15, 15));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        JButton btnGestionModelos = new JButton("Gestionar Modelos");
        JButton btnGestionFotografos = new JButton("Gestionar Fotógrafos");
        JButton btnGestionLugares = new JButton("Gestionar Lugares");
        JButton btnGestionEventos = new JButton("Crear Evento");
        JButton btnVerEventos = new JButton("Consultar Eventos"); // NUEVO BOTON
        JButton btnSalir = new JButton("Salir y Guardar");

        // Agregar acciones (Listeners) a los botones
        btnGestionModelos.addActionListener(e -> {
            String[] opciones = { "Registrar Nuevo", "Ver Lista de Activos" };
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué acción desea realizar con los Modelos?",
                    "Gestión de Modelos", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            if (eleccion == 0) { // Registrar
                new ModeloFormFrame(this, agencia).setVisible(true);
                this.setVisible(false);
            } else if (eleccion == 1) { // Ver Lista
                new VerListasFrame(this, agencia, "Modelo").setVisible(true);
                this.setVisible(false);
            }
        });

        btnGestionFotografos.addActionListener(e -> {
            String[] opciones = { "Registrar Nuevo", "Ver Lista de Activos" };
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué acción desea realizar con los Fotógrafos?",
                    "Gestión de Fotógrafos", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            if (eleccion == 0) {
                new FotografoFormFrame(this, agencia).setVisible(true);
                this.setVisible(false);
            } else if (eleccion == 1) {
                new VerListasFrame(this, agencia, "Fotografo").setVisible(true);
                this.setVisible(false);
            }
        });

        btnGestionLugares.addActionListener(e -> {
            String[] opciones = { "Registrar Nuevo", "Ver Lista de Activos" };
            int eleccion = JOptionPane.showOptionDialog(this, "¿Qué acción desea realizar con los Lugares?",
                    "Gestión de Lugares", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,
                    null, opciones, opciones[0]);

            if (eleccion == 0) {
                new LugarFormFrame(this, agencia).setVisible(true);
                this.setVisible(false);
            } else if (eleccion == 1) {
                new VerListasFrame(this, agencia, "Lugar").setVisible(true);
                this.setVisible(false);
            }
        });

        btnGestionEventos.addActionListener(e -> {
            new EventoFormFrame(this, agencia).setVisible(true);
            this.setVisible(false);
        });

        // NUEVO LISTENER
        btnVerEventos.addActionListener(e -> {
            new VerEventosFrame(this, agencia).setVisible(true);
            this.setVisible(false);
        });

        btnSalir.addActionListener(e -> {
            // Se debe guardar antes de que termine la ejecución
            agencia.guardarDatos();
            JOptionPane.showMessageDialog(this, "Datos guardados. Saliendo del sistema...");
            System.exit(0);
        });

        // Añadir botones al panel de botones
        panelBotones.add(btnGestionModelos);
        panelBotones.add(btnGestionFotografos);
        panelBotones.add(btnGestionLugares);
        panelBotones.add(btnGestionEventos);
        panelBotones.add(btnVerEventos); // NUEVO BOTON AL PANEL
        panelBotones.add(btnSalir);

        // Añadir panel de botones al panel principal
        panelPrincipal.add(panelBotones, BorderLayout.CENTER);

        // Finalmente, añadir el panel principal a la ventana (JFrame)
        add(panelPrincipal);
    }

    /**
     * Punto de entrada de la aplicación. (Main)
     */
    public static void main(String[] args) {
        // Ejecución correcta de Swing (En el hilo de despacho de eventos)
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame frame = new MainMenuFrame();
            frame.setVisible(true);
        });
    }
}
