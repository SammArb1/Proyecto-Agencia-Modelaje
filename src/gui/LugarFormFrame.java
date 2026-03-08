package gui;

import modelo.Lugar;
import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

public class LugarFormFrame extends JFrame {

    private JFrame menuPrincipal;
    private Agencia agencia;

    private JTextField txtNombre, txtDireccion, txtCiudad, txtCapacidad;
    private JComboBox<String> cbTipo;

    public LugarFormFrame(JFrame menuPrincipal, Agencia agencia) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;

        iniciarVentana();
        iniciarComponentes();
    }

    private void iniciarVentana() {
        setTitle("Registrar Lugar");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Formulario de Lugares", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtNombre = new JTextField();
        txtDireccion = new JTextField();
        txtCiudad = new JTextField();
        txtCapacidad = new JTextField();

        String[] tipos = { "Hotel", "Estudio de Fotografía", "Pasarela", "Salón Social", "Al Aire Libre" };
        cbTipo = new JComboBox<>(tipos);

        panelForm.add(new JLabel("Nombre del Lugar:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Dirección:"));
        panelForm.add(txtDireccion);
        panelForm.add(new JLabel("Ciudad:"));
        panelForm.add(txtCiudad);
        panelForm.add(new JLabel("Capacidad Máxima:"));
        panelForm.add(txtCapacidad);
        panelForm.add(new JLabel("Tipo de Espacio:"));
        panelForm.add(cbTipo);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar Lugar");
        JButton btnVolver = new JButton("Volver al Menú");

        btnGuardar.addActionListener(e -> guardarLugar());
        btnVolver.addActionListener(e -> {
            this.dispose();
            menuPrincipal.setVisible(true);
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void guardarLugar() {
        try {
            String nombre = txtNombre.getText();
            String dir = txtDireccion.getText();
            String ciudad = txtCiudad.getText();
            String tipo = (String) cbTipo.getSelectedItem();

            if (nombre.isEmpty() || txtCapacidad.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Revisar campos vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int cap = Integer.parseInt(txtCapacidad.getText());

            // ¡Comunicación con lógica!
            Lugar l = new Lugar(nombre, dir, ciudad, tipo, cap);
            boolean exito = agencia.agregarLugar(l);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Lugar registrado satisfactoriamente.");
                txtNombre.setText("");
                txtDireccion.setText("");
                txtCiudad.setText("");
                txtCapacidad.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Memoria llena.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La capacidad debe ser numérica.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
