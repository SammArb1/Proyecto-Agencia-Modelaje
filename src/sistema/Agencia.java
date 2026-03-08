package sistema;

import java.io.*;
import modelo.Modelo;
import modelo.Fotografo;
import modelo.Evento;
import modelo.Lugar;
import persistencia.Persistencia;

public class Agencia {

    private Modelo[] modelos;
    private Fotografo[] fotografos;
    private Evento[] eventos;
    private Lugar[] lugares; // Para la GUI

    private int numModelos;
    private int numFotografos;
    private int numEventos;
    private int numLugares; // Para la GUI

    public Agencia(int maxModelos, int maxFotografos, int maxEventos) {

        modelos = new Modelo[maxModelos];
        fotografos = new Fotografo[maxFotografos];
        eventos = new Evento[maxEventos];
        lugares = new Lugar[100]; // Por defecto para lugares

        numModelos = 0;
        numFotografos = 0;
        numEventos = 0;
        numLugares = 0;
    }

    // GESTIONAR MODELOS

    public boolean agregarModelo(Modelo m) {

        if (numModelos < modelos.length) {
            modelos[numModelos] = m;
            numModelos++;
            return true;
        } else {
            System.out.println("No se pueden agregar más modelos");
            return false;
        }
    }

    public void mostrarModelos() {

        for (int i = 0; i < numModelos; i++) {
            System.out.println(modelos[i]);
        }
    }

    public Modelo[] getModelos() {
        Modelo[] registrados = new Modelo[numModelos];
        for (int i = 0; i < numModelos; i++)
            registrados[i] = modelos[i];
        return registrados;
    }

    // GESTIONAR FOTOGRAFOS

    public boolean agregarFotografo(Fotografo f) {

        if (numFotografos < fotografos.length) {
            fotografos[numFotografos] = f;
            numFotografos++;
            return true;
        } else {
            System.out.println("No se pueden agregar más fotografos");
            return false;
        }
    }

    public void mostrarFotografos() {

        for (int i = 0; i < numFotografos; i++) {
            System.out.println(fotografos[i]);
        }
    }

    public Fotografo[] getFotografos() {
        Fotografo[] registrados = new Fotografo[numFotografos];
        for (int i = 0; i < numFotografos; i++)
            registrados[i] = fotografos[i];
        return registrados;
    }

    // GESTIONAR EVENTOS

    public boolean agregarEvento(Evento e) {

        if (numEventos < eventos.length) {
            eventos[numEventos] = e;
            numEventos++;
            return true;
        } else {
            System.out.println("No se pueden agregar más eventos");
            return false;
        }
    }

    public void mostrarEventos() {

        for (int i = 0; i < numEventos; i++) {
            System.out.println(eventos[i].getNombreEvento());
        }
    }

    public Evento[] getEventos() {
        Evento[] registrados = new Evento[numEventos];
        for (int i = 0; i < numEventos; i++)
            registrados[i] = eventos[i];
        return registrados;
    }

    // ==========================================
    // GESTIÓN DE LUGARES
    // ==========================================
    /**
     * Registra un lugar en la base de datos temporal de la agencia.
     * 
     * @param l Objeto de tipo Lugar.
     * @return true si se agregó con éxito, false en caso contrario.
     */
    public boolean agregarLugar(Lugar l) {
        if (numLugares < lugares.length) {
            lugares[numLugares] = l;
            numLugares++;
            return true;
        } else {
            System.out.println("No se pueden agregar más lugares");
            return false;
        }
    }

    public Lugar[] getLugares() {
        Lugar[] registrados = new Lugar[numLugares];
        for (int i = 0; i < numLugares; i++)
            registrados[i] = lugares[i];
        return registrados;
    }

    // RELACIONAR PERSONAS CON EVENTOS

    public Evento buscarEvento(String nombre) {

        for (int i = 0; i < numEventos; i++) {

            if (eventos[i].getNombreEvento().equals(nombre)) {
                return eventos[i];
            }
        }

        return null;
    }

    public void agregarModeloEvento(String nombreEvento, Modelo m) {

        Evento e = buscarEvento(nombreEvento);

        if (e != null) {
            e.agregarModelo(m);
        } else {
            System.out.println("Evento no encontrado");
        }
    }

    public void agregarFotografoEvento(String nombreEvento, Fotografo f) {

        Evento e = buscarEvento(nombreEvento);

        if (e != null) {
            e.agregarFotografo(f);
        } else {
            System.out.println("Evento no encontrado");
        }
    }

    // GUARDAR MODELOS EN ARCHIVO

    public void guardarModelos() throws IOException {

        BufferedWriter bw = new BufferedWriter(new FileWriter("modelos.txt"));

        for (int i = 0; i < numModelos; i++) {

            bw.write(modelos[i].toString());
            bw.newLine();
        }

        bw.close();
    }

    // ==========================================
    // MÉTODOS DE PERSISTENCIA Y CARGA DE DATOS
    // ==========================================
    /**
     * Guarda el estado actual de todas las listas maestras del sistema en archivos
     * texto.
     * Esta funcionalidad asegura que los datos no se pierdan al cerrar la sesión.
     */
    public void guardarDatos() {
        try {
            guardarModelos();
            Persistencia persistencia = new Persistencia();
            persistencia.guardarFotografos(fotografos, numFotografos);
            persistencia.guardarLugares(lugares, numLugares);
            persistencia.guardarEventos(eventos, numEventos);
        } catch (IOException e) {
            System.out.println("No se pudieron guardar los modelos (IO).");
        }
    }

    public void cargarDatos() {
        Persistencia persistencia = new Persistencia();
        numFotografos = persistencia.cargarFotografos(fotografos);
        numLugares = persistencia.cargarLugar(lugares);
        numEventos = persistencia.cargarEventos(eventos);
        // Modelos no estipulados para carga en esta iteración de prueba.
    }

}
