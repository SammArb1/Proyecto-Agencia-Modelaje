package gui;

import modelo.Evento;
import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana (JFrame) para consultar la información de los eventos
 * registrados en el sistema, mostrando sus detalles y participantes.
 */
public class VerEventosFrame extends JFrame {

    // Referencias
    private JFrame menuPrincipal;
    private Agencia agencia;

    // Componentes de la UI
    private JComboBox<Evento> cbEventos;
    private JTextArea txtDetalles;

    public VerEventosFrame(JFrame menuPrincipal, Agencia agencia) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;

        iniciarVentana();
        iniciarComponentes();
    }

    private void iniciarVentana() {
        setTitle("Consultar Eventos Registrados");
        setSize(550, 500);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Título Superior ---
        JLabel lblTitulo = new JLabel("Lista de Eventos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Panel Central ---
        JPanel panelCentro = new JPanel(new BorderLayout(5, 10));

        // Selector superior (Combo Box)
        JPanel panelSelector = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSelector.add(new JLabel("Seleccione un evento: "));

        // Obtener arreglo de eventos de Agencia (sin colecciones de util!)
        Evento[] eventosRegistrados = agencia.getEventos();
        cbEventos = new JComboBox<>(eventosRegistrados);
        panelSelector.add(cbEventos);

        panelCentro.add(panelSelector, BorderLayout.NORTH);

        // Área de texto (multilínea) para mostrar los detalles del evento
        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Agregar "scroll" en caso de que el texto sea muy largo
        JScrollPane scrollPane = new JScrollPane(txtDetalles);
        panelCentro.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        // --- Acciones de Cambio de Evento ---
        cbEventos.addActionListener(e -> mostrarInfoEventoSeleccionado());

        // Comprobar si hay eventos y mostrar información del primero por defecto
        if (eventosRegistrados.length > 0) {
            mostrarInfoEventoSeleccionado();
        } else {
            txtDetalles.setText("No hay eventos registrados en el sistema actualmente.");
            cbEventos.setEnabled(false);
        }

        // --- Botón Volver (Panel Sur) ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver al Menú Principal");

        btnVolver.addActionListener(e -> {
            this.dispose(); // Destruimos esta ventana
            menuPrincipal.setVisible(true); // Restauramos el menú
        });

        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Construye y carga el texto informativo del evento en el JTextArea,
     * iterando por los arreglos para sacar los participantes
     * como fue solicitado por el profesor.
     */
    private void mostrarInfoEventoSeleccionado() {
        Evento evento = (Evento) cbEventos.getSelectedItem();

        if (evento != null) {
            StringBuilder info = new StringBuilder();

            // 1. Mostrar Detalles Básicos (Llamada polimórfica a EventoPublico o Privado)
            info.append(evento.mostrarDetalles()).append("\n");

            // 2. Extraer del arreglo de Modelos del Evento
            info.append("\n==================================");
            info.append("\n    MODELOS ASIGNADOS AL EVENTO   ");
            info.append("\n==================================\n");

            modelo.Modelo[] modelos = evento.getModelo();
            int limitMod = evento.getNumModelos();

            if (limitMod == 0) {
                info.append("Ningún modelo asignado aún.\n");
            } else {
                for (int i = 0; i < limitMod; i++) {
                    info.append("- ").append(modelos[i].getNombre())
                            .append(" (").append(modelos[i].getCategoria()).append(")\n");
                }
            }

            // 3. Extraer del arreglo de Fotógrafos del Evento
            info.append("\n==================================");
            info.append("\n  FOTÓGRAFOS ASIGNADOS AL EVENTO  ");
            info.append("\n==================================\n");

            modelo.Fotografo[] fotos = evento.getFotografo();
            int limitFot = evento.getNumFotografos();

            if (limitFot == 0) {
                info.append("Ningún fotógrafo asignado aún.\n");
            } else {
                for (int i = 0; i < limitFot; i++) {
                    info.append("- ").append(fotos[i].getNombre())
                            .append(" - Esp: ").append(fotos[i].getEspecialidad()).append("\n");
                }
            }

            // Aplicar resultado a la interfaz y mover el scroll al inicio
            txtDetalles.setText(info.toString());
            txtDetalles.setCaretPosition(0);
        }
    }
}
