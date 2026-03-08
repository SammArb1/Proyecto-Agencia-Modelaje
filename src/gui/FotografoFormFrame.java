package gui;

import modelo.Fotografo;
import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

public class FotografoFormFrame extends JFrame {

    private JFrame menuPrincipal;
    private Agencia agencia;

    private JTextField txtNombre, txtId, txtContacto, txtEspecialidad, txtExperiencia;

    public FotografoFormFrame(JFrame menuPrincipal, Agencia agencia) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;

        iniciarVentana();
        iniciarComponentes();
    }

    private void iniciarVentana() {
        setTitle("Registrar Fotógrafo");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Formulario de Fotógrafos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        txtNombre = new JTextField();
        txtId = new JTextField();
        txtContacto = new JTextField();
        txtEspecialidad = new JTextField();
        txtExperiencia = new JTextField();

        panelForm.add(new JLabel("Nombre Completo:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Identificación:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Contacto:"));
        panelForm.add(txtContacto);
        panelForm.add(new JLabel("Especialidad (ej. Producto):"));
        panelForm.add(txtEspecialidad);
        panelForm.add(new JLabel("Años de Experiencia:"));
        panelForm.add(txtExperiencia);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar Fotógrafo");
        JButton btnVolver = new JButton("Volver al Menú");

        btnGuardar.addActionListener(e -> guardarFotografo());
        btnVolver.addActionListener(e -> {
            this.dispose();
            menuPrincipal.setVisible(true);
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private void guardarFotografo() {
        try {
            String nombre = txtNombre.getText();
            String id = txtId.getText();
            int contacto = Integer.parseInt(txtContacto.getText());
            String especialidad = txtEspecialidad.getText();

            if (nombre.isEmpty() || txtExperiencia.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor llene todos los campos obligatorios.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int exp = Integer.parseInt(txtExperiencia.getText());

            // ¡Comunicación con lógica!
            Fotografo f = new Fotografo(nombre, id, contacto, especialidad, exp);
            boolean exito = agencia.agregarFotografo(f);

            if (exito) {
                JOptionPane.showMessageDialog(this, "Fotógrafo guardado con éxito.");
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Arreglo lleno.", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El contacto y la experiencia deben ser números enteros.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtId.setText("");
        txtContacto.setText("");
        txtEspecialidad.setText("");
        txtExperiencia.setText("");
    }
}
