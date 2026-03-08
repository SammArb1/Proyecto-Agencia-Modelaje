package gui;

import modelo.Modelo;
import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

/**
 * Ventana (JFrame) para registrar crear nuevos modelos en el sistema.
 */
public class ModeloFormFrame extends JFrame {

    // Referencia al menú principal para poder volver a él
    private JFrame menuPrincipal;
    // Referencia a la agencia para guardar el modelo
    private Agencia agencia;

    // Campos de texto para que el usuario escriba
    private JTextField txtNombre, txtId, txtContacto, txtCodigo, txtEstatura;
    private JComboBox<String> cbCategoria;
    private JCheckBox chkDisponibilidad;

    /**
     * Constructor que recibe el menú del que viene, y la agencia actual.
     */
    public ModeloFormFrame(JFrame menuPrincipal, Agencia agencia) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;

        iniciarVentana();
        iniciarComponentes();
    }

    private void iniciarVentana() {
        setTitle("Registrar Modelo");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Controlamos nosotros el cierre
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Formulario de Modelos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Panel de Formulario (Grid para etiquetas y campos de texto)
        // 7 filas y 2 columnas
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Inicializar componentes
        txtNombre = new JTextField();
        txtId = new JTextField();
        txtContacto = new JTextField();
        txtCodigo = new JTextField();
        txtEstatura = new JTextField();

        // JComboBox de Ejemplo en lugar de Campo de texto para evitar errores de tipeo
        String[] categorias = { "Pasarela", "Comercial", "Fitness", "Alta Costura" };
        cbCategoria = new JComboBox<>(categorias);

        chkDisponibilidad = new JCheckBox("Disponible actualmente");
        chkDisponibilidad.setSelected(true); // Seleccionado por defecto

        // Añadir a panel (Etiqueta -> Campo)
        panelForm.add(new JLabel("Nombre Completo:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Identificación (CC/NIT):"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Contacto (Tel./Email):"));
        panelForm.add(txtContacto);
        panelForm.add(new JLabel("Código Único Modelo:"));
        panelForm.add(txtCodigo);
        panelForm.add(new JLabel("Estatura (ej. 1.75):"));
        panelForm.add(txtEstatura);
        panelForm.add(new JLabel("Categoría:"));
        panelForm.add(cbCategoria);
        panelForm.add(new JLabel("Disponibilidad:"));
        panelForm.add(chkDisponibilidad);

        panelPrincipal.add(panelForm, BorderLayout.CENTER);

        // Panel de botones (Sur)
        JPanel panelBotones = new JPanel(new FlowLayout());

        JButton btnGuardar = new JButton("Guardar Modelo");
        JButton btnVolver = new JButton("Volver al Menú");

        btnGuardar.addActionListener(e -> guardarModelo());

        // Listener para regresar
        btnVolver.addActionListener(e -> {
            this.dispose(); // Destruir ventana actual
            menuPrincipal.setVisible(true); // Mostrar menú de nuevo
        });

        panelBotones.add(btnGuardar);
        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    /**
     * Lógica para extraer los datos, crear el objeto de dominio y enviarlo a
     * Agencia.
     */
    private void guardarModelo() {
        try {
            // 1. Obtener y validar datos
            String nombre = txtNombre.getText();
            String id = txtId.getText();
            int contacto = Integer.parseInt(txtContacto.getText());
            String codigo = txtCodigo.getText();

            if (nombre.isEmpty() || id.isEmpty() || codigo.isEmpty() || txtEstatura.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor llene todos los campos obligatorios.", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            int estatura = Integer.parseInt(txtEstatura.getText());
            String categoria = (String) cbCategoria.getSelectedItem();
            boolean disponible = chkDisponibilidad.isSelected();

            // 2. Crear instancia del Modelo (Polimorfismo, de clase abstracta Persona)
            Modelo nuevoModelo = new Modelo(nombre, id, contacto, codigo, estatura, categoria, disponible);

            // 3. ¡AQUÍ SE LLAMA A LA LÓGICA DEL SISTEMA!
            boolean exito = agencia.agregarModelo(nuevoModelo);

            if (exito) {
                JOptionPane.showMessageDialog(this,
                        "Modelo guardado exitosamente.\nDatos:\n" + nuevoModelo.toString());
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo registrar. Almacenamiento lleno.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La estatura y el contacto deben ser números enteros válidos.",
                    "Dato Inválido",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtId.setText("");
        txtContacto.setText("");
        txtCodigo.setText("");
        txtEstatura.setText("");
        cbCategoria.setSelectedIndex(0);
        chkDisponibilidad.setSelected(true);
    }
}
