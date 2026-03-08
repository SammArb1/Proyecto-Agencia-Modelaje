package gui;

import modelo.*;
import sistema.Agencia;
import javax.swing.*;
import java.awt.*;

public class EventoFormFrame extends JFrame {

    private JFrame menuPrincipal;
    private Agencia agencia;

    private JTextField txtNombre, txtFecha;
    private JComboBox<String> cbTipoEvento;
    private JComboBox<Lugar> cbLugar;

    // Campos específicos para Evento Público
    private JTextField txtCapacidadPub, txtPatrocinador;

    // Campos específicos para Evento Privado
    private JTextField txtCliente, txtConfidencialidad;

    // Panel que cambiará dinámicamente según el tipo de evento
    private JPanel panelDinamico;

    // Elementos para asignar modelos y fotógrafos
    private JComboBox<Modelo> cbModelos;
    private JComboBox<Fotografo> cbFotografos;
    private Evento eventoActualEnCreacion = null;

    public EventoFormFrame(JFrame menuPrincipal, Agencia agencia) {
        this.menuPrincipal = menuPrincipal;
        this.agencia = agencia;

        iniciarVentana();
        iniciarComponentes();
    }

    private void iniciarVentana() {
        setTitle("Gestión de Eventos");
        setSize(550, 600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void iniciarComponentes() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Crear Evento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // -- PANEL CENTRAL CON FORMULARIO --
        JPanel panelFormulario = new JPanel(new BorderLayout());

        // Formulario Estático
        JPanel panelEstatico = new JPanel(new GridLayout(4, 2, 5, 5));
        panelEstatico.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        txtNombre = new JTextField();
        txtFecha = new JTextField("DD/MM/AAAA");

        String[] tipos = { "Público", "Privado" };
        cbTipoEvento = new JComboBox<>(tipos);

        // Llenar combo de lugares con los lugares de la agencia
        Lugar[] lugaresRegistrados = agencia.getLugares();
        cbLugar = new JComboBox<>(lugaresRegistrados);

        panelEstatico.add(new JLabel("Nombre Evento:"));
        panelEstatico.add(txtNombre);
        panelEstatico.add(new JLabel("Fecha:"));
        panelEstatico.add(txtFecha);
        panelEstatico.add(new JLabel("Tipo Evento:"));
        panelEstatico.add(cbTipoEvento);
        panelEstatico.add(new JLabel("Lugar:"));
        panelEstatico.add(cbLugar);

        // Cambio dinámico de campos
        panelDinamico = new JPanel(new CardLayout());

        // -- Panel Público --
        JPanel panelPublico = new JPanel(new GridLayout(2, 2, 5, 5));
        txtCapacidadPub = new JTextField();
        txtPatrocinador = new JTextField();
        panelPublico.add(new JLabel("Capacidad Público:"));
        panelPublico.add(txtCapacidadPub);
        panelPublico.add(new JLabel("Patrocinador:"));
        panelPublico.add(txtPatrocinador);

        // -- Panel Privado --
        JPanel panelPrivado = new JPanel(new GridLayout(2, 2, 5, 5));
        txtCliente = new JTextField();
        txtConfidencialidad = new JTextField();
        panelPrivado.add(new JLabel("Cliente VIP:"));
        panelPrivado.add(txtCliente);
        panelPrivado.add(new JLabel("Nivel Confidencial:"));
        panelPrivado.add(txtConfidencialidad);

        panelDinamico.add(panelPublico, "Público");
        panelDinamico.add(panelPrivado, "Privado");

        cbTipoEvento.addActionListener(e -> {
            CardLayout cl = (CardLayout) (panelDinamico.getLayout());
            cl.show(panelDinamico, (String) cbTipoEvento.getSelectedItem());
        });

        // Agrupar ambos paneles
        JPanel panelCampos = new JPanel(new BorderLayout());
        panelCampos.add(panelEstatico, BorderLayout.NORTH);
        panelCampos.add(panelDinamico, BorderLayout.CENTER);

        // Botón de crear evento básico
        JButton btnCrearEvento = new JButton("Crear Evento Base");
        panelCampos.add(btnCrearEvento, BorderLayout.SOUTH);

        panelFormulario.add(panelCampos, BorderLayout.NORTH);

        // --- PANEL DE ASIGNACIONES (Modelos, Fotógrafos) ---
        JPanel panelAsignaciones = new JPanel(new GridLayout(4, 2, 5, 5));
        panelAsignaciones.setBorder(BorderFactory.createTitledBorder("2. Asignar Personal al Evento (Opcional)"));

        // Obtener arrays de la agencia (CUMPLIENDO REGLA DE NO USAR COLLECTIONS)
        Modelo[] modelos = agencia.getModelos();
        Fotografo[] fotografos = agencia.getFotografos();

        cbModelos = new JComboBox<>(modelos);
        cbFotografos = new JComboBox<>(fotografos);

        JButton btnAsignarModelo = new JButton("Asignar Modelo");
        JButton btnAsignarFotografo = new JButton("Asignar Fotógrafo");

        panelAsignaciones.add(new JLabel("Seleccionar Modelo:"));
        panelAsignaciones.add(cbModelos);
        panelAsignaciones.add(new JLabel("")); // Espacio vacío
        panelAsignaciones.add(btnAsignarModelo);

        panelAsignaciones.add(new JLabel("Seleccionar Fotógrafo:"));
        panelAsignaciones.add(cbFotografos);
        panelAsignaciones.add(new JLabel(""));
        panelAsignaciones.add(btnAsignarFotografo);

        // Deshabilitar por defecto hasta crear el evento
        Component[] componentesAsignacion = panelAsignaciones.getComponents();
        for (Component c : componentesAsignacion) {
            c.setEnabled(false);
        }

        panelFormulario.add(panelAsignaciones, BorderLayout.CENTER);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);

        // -- ACCIONES --
        btnCrearEvento.addActionListener(e -> {
            boolean exito = crearEvento();
            if (exito) {
                // Habilitamos paneles de asigación
                for (Component c : componentesAsignacion) {
                    c.setEnabled(true);
                }
            }
        });

        btnAsignarModelo.addActionListener(e -> {
            Modelo m = (Modelo) cbModelos.getSelectedItem();
            if (eventoActualEnCreacion != null && m != null) {
                if (eventoActualEnCreacion.agregarModelo(m))
                    JOptionPane.showMessageDialog(this, "Modelo añadido al evento.");
                else
                    JOptionPane.showMessageDialog(this, "Cupo lleno para modelos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAsignarFotografo.addActionListener(e -> {
            Fotografo f = (Fotografo) cbFotografos.getSelectedItem();
            if (eventoActualEnCreacion != null && f != null) {
                if (eventoActualEnCreacion.agregarFotografo(f))
                    JOptionPane.showMessageDialog(this, "Fotógrafo añadido al evento.");
                else
                    JOptionPane.showMessageDialog(this, "Cupo lleno para fotógrafos.", "Error",
                            JOptionPane.ERROR_MESSAGE);
            }
        });

        // Panel de botones final (Sur)
        JPanel panelBotones = new JPanel();
        JButton btnVolver = new JButton("Finalizar y Volver al Menú");

        btnVolver.addActionListener(e -> {
            this.dispose();
            menuPrincipal.setVisible(true);
        });

        panelBotones.add(btnVolver);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    private boolean crearEvento() {
        String nombre = txtNombre.getText();
        String fecha = txtFecha.getText();
        Lugar lugar = (Lugar) cbLugar.getSelectedItem();
        String tipo = (String) cbTipoEvento.getSelectedItem();

        if (nombre.isEmpty() || lugar == null) {
            JOptionPane.showMessageDialog(this, "Faltan datos obligatorios o no hay lugares creados.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (tipo.equals("Público")) {
            try {
                int cap = Integer.parseInt(txtCapacidadPub.getText());
                String pat = txtPatrocinador.getText();
                eventoActualEnCreacion = new EventoPublico(nombre, fecha, new Lugar[] { lugar }, new Modelo[50],
                        new Fotografo[20], cap, pat);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Capacidad debe ser número.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            String cli = txtCliente.getText();
            String confText = txtConfidencialidad.getText();
            char conf = (confText.length() > 0) ? confText.charAt(0) : 'N';
            eventoActualEnCreacion = new EventoPrivado(nombre, fecha, new Lugar[] { lugar }, new Modelo[50],
                    new Fotografo[20], cli, conf);
        }

        // GUARDAR EN EL SISTEMA
        boolean guardado = agencia.agregarEvento(eventoActualEnCreacion);
        if (guardado) {
            JOptionPane.showMessageDialog(this, "Evento Base Creado. Ahora puede asignar personal.");
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Memoria de eventos llena.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
