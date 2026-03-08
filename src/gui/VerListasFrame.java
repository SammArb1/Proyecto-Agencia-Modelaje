package gui;

import sistema.Agencia;
import modelo.Modelo;
import modelo.Fotografo;
import modelo.Lugar;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana genérica para ver las listas de Modelos, Fotógrafos o Lugares
 * registrados.
 */
public class VerListasFrame extends JFrame {

    private JFrame menuPrincipal;
    private Agencia agencia;
    private String tipoEntidad; // "Modelo", "Fotografo", o "Lugar"

    private JTextArea txtDetalles;

    public VerListasFrame(JFrame menuPrincipal, Agencia agencia, String tipoEntidad) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;
        this.tipoEntidad = tipoEntidad;

        iniciarVentana();
        iniciarComponentes();
        cargarInformacion();
    }

    private void iniciarVentana() {
        setTitle("Lista de " + tipoEntidad + "s Activos");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // --- Título ---
        JLabel lblTitulo = new JLabel("Directorio de " + tipoEntidad + "s", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Área de texto ---
        txtDetalles = new JTextArea();
        txtDetalles.setEditable(false);
        txtDetalles.setFont(new Font("Monospaced", Font.PLAIN, 14));
        txtDetalles.setLineWrap(true);
        txtDetalles.setWrapStyleWord(true);
        txtDetalles.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JScrollPane scrollPane = new JScrollPane(txtDetalles);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // --- Botón Volver ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVolver = new JButton("Volver al Menú");

        btnVolver.addActionListener(e -> {
            this.dispose();
            menuPrincipal.setVisible(true);
        });

        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void cargarInformacion() {
        StringBuilder sb = new StringBuilder();

        if (tipoEntidad.equals("Modelo")) {
            Modelo[] lista = agencia.getModelos();
            if (lista.length == 0) {
                sb.append("No hay modelos registrados.");
            } else {
                for (Modelo m : lista) {
                    sb.append("• ").append(m.getNombre())
                            .append("\n  - Disp: ").append(m.isDisponibilidad() ? "Sí" : "No")
                            .append("\n  - Cód: ").append(m.getCodigoModelo())
                            .append("\n  - Cat: ").append(m.getCategoria())
                            .append("\n  - Con: ").append(m.getContacto())
                            .append("\n\n");
                }
            }
        } else if (tipoEntidad.equals("Fotografo")) {
            Fotografo[] lista = agencia.getFotografos();
            if (lista.length == 0) {
                sb.append("No hay fotógrafos registrados.");
            } else {
                for (Fotografo f : lista) {
                    sb.append("• ").append(f.getNombre())
                            .append("\n  - Esp: ").append(f.getEspecialidad())
                            .append("\n  - Exp: ").append(f.getAñosExperiencia()).append(" años")
                            .append("\n  - Con: ").append(f.getContacto())
                            .append("\n\n");
                }
            }
        } else if (tipoEntidad.equals("Lugar")) {
            Lugar[] lista = agencia.getLugares();
            if (lista.length == 0) {
                sb.append("No hay lugares registrados.");
            } else {
                for (Lugar l : lista) {
                    sb.append("• ").append(l.getNombre())
                            .append("\n  - Dir: ").append(l.getDireccion())
                            .append("\n  - Ciu: ").append(l.getCiudad())
                            .append("\n  - Tip: ").append(l.getTipoLugar())
                            .append("\n  - Cap: ").append(l.getCapacidad()).append(" pers.")
                            .append("\n\n");
                }
            }
        }

        txtDetalles.setText(sb.toString());
        txtDetalles.setCaretPosition(0);
    }
}
