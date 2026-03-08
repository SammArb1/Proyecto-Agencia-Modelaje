package persistencia;

import modelo.*;
import java.io.*;

public class Persistencia {

    private static final String ARCHIVO_MODELOS = "modelos.txt";
    private static final String ARCHIVO_FOTOGRAFOS = "fotografos.txt";
    private static final String ARCHIVO_LUGARES = "lugares.txt";
    private static final String ARCHIVO_EVENTOS = "eventos.txt";

    public void guardarModelos(Modelo[] modelos, int cantidad) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_MODELOS));
            for (int i = 0; i < cantidad; i++) {
                Modelo m = modelos[i];
                bw.write(
                        m.getCodigoModelo() + ";" +
                                m.getIdentificacion() + ";" +
                                m.getNombre() + ";" +
                                m.getCategoria() + ";" +
                                m.getEstatura() + ";" +
                                m.isDisponibilidad() + ";" +
                                m.getContacto()
                );
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error guardando modelos");
        }
    }

    public int cargarModelos(Modelo[] modelos) {
        File archivo = new File(ARCHIVO_MODELOS);
        if (!archivo.exists()) {
            return 0;
        }
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                String codigoModelo = partes[0];
                String identificacion = partes[1];
                String nombre = partes[2];
                String categoria = partes[3];
                int estatura = Integer.parseInt(partes[4]);
                boolean disponibilidad = Boolean.parseBoolean(partes[5]);
                int contacto = (partes.length > 6 && !partes[6].equals("N/A")) ? Integer.parseInt(partes[6]) : 0;

                modelos[contador] = new Modelo(
                        nombre, identificacion, contacto, codigoModelo, estatura, categoria, disponibilidad);
                contador++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contador;
    }

    public void guardarFotografos(Fotografo[] fotografos, int cantidad) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_FOTOGRAFOS));
            for (int i = 0; i < cantidad; i++) {
                Fotografo f = fotografos[i];
                bw.write(
                        f.getNombre() + ";" +
                                f.getEspecialidad() + ";" +
                                f.getEspecialidad() + ";" +
                                f.getAñosExperiencia() + ";" +
                                f.getIdentificacion() + ";" +
                                f.getContacto());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error guardando fotografos");
        }
    }

    public int cargarFotografos(Fotografo[] fotografos) {
        File archivo = new File(ARCHIVO_FOTOGRAFOS);
        if (!archivo.exists()) {
            return 0;
        }
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                String nombre = partes[0];
                String especialidad = partes[1];
                int aniosExperiencia = Integer.parseInt(partes[2]);
                String identificacion = partes.length > 3 ? partes[3] : "N/A";
                int contacto = (partes.length > 4 && !partes[4].equals("N/A")) ? Integer.parseInt(partes[4]) : 0;

                fotografos[contador] = new Fotografo(
                        nombre, identificacion, contacto, especialidad, aniosExperiencia);
                contador++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contador;
    }

    public void guardarLugares(Lugar[] lugares, int cantidad) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_LUGARES));
            for (int i = 0; i < cantidad; i++) {
                Lugar l = lugares[i];
                bw.write(
                        l.getCiudad() + ";" +
                                l.getNombre() + ";" +
                                l.getDireccion() + ";" +
                                l.getCapacidad() + ";" +
                                l.getTipoLugar());
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            System.out.println("Error guardando lugares");
        }
    }

    public int cargarLugar(Lugar[] lugares) {
        File archivo = new File(ARCHIVO_LUGARES);
        if (!archivo.exists()) {
            return 0;
        }
        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");
                // Constructor nuestro: Lugar(nombre, direccion, ciudad, capacidad, tipoLugar)
                String ciudad = partes[0];
                String nombre = partes[1];
                String direccion = partes[2];
                int capacidad = Integer.parseInt(partes[3]);
                String tipoLugar = partes[4];

                lugares[contador] = new Lugar(
                        nombre, direccion, ciudad, tipoLugar, capacidad);
                contador++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contador;
    }

    public void guardarEventos(Evento[] eventos, int cantidad) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_EVENTOS));

            for (int i = 0; i < cantidad; i++) {
                Evento e = eventos[i];

                // Extraemos el primer fotógrafo y modelo para imitar `e.getFotografo()`
                String nombreFotografo = (e.getNumFotografos() > 0) ? e.getFotografo()[0].getNombre()
                        : "Sin fotografo";
                String nombreModelo = (e.getNumModelos() > 0) ? e.getModelo()[0].getNombre()
                        : "Sin modelo";
                String nombreLugar = (e.getLugarObjeto() != null) ? e.getLugarObjeto().getNombre() : "Sin lugar";

                bw.write(
                        e.getNombreEvento() + ";" +
                                e.getFecha() + ";" +
                                nombreFotografo + ";" +
                                nombreLugar + ";" +
                                nombreModelo);
                bw.newLine();
            }

            bw.close();
        } catch (IOException ex) {
            System.out.println("Error guardando eventos");
        }
    }

    public int cargarEventos(Evento[] eventos) {
        File archivo = new File("eventos.txt");
        if (!archivo.exists()) {
            return 0;
        }

        int contador = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(archivo));
            String linea;

            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(";");

                String nombre = partes[0];
                String fecha = partes[1];
                String fotografo = partes[2];
                String nombreLugar = partes[3];
                String modelo = partes[4];

                // Se fuerza la instanciación de un EventoPublico por diseño arquitectónico
                // base.
                // Los atributos obligatorios no hallados en el texto se llenan con datos por
                // defecto.
                Lugar lugarDummy = new Lugar(nombreLugar, "N/A", "N/A", "N/A", 0);

                eventos[contador] = new EventoPublico(
                        nombre,
                        fecha,
                        new Lugar[] { lugarDummy },
                        new Modelo[50],
                        new Fotografo[20],
                        0, // Capacidad
                        "N/A" // Patrocinador
                );

                // Se simula la carga asociando entidades mínimas validables a los arreglos del
                // evento.
                eventos[contador].agregarModelo(new Modelo(modelo, 0, "N/A", true));
                eventos[contador].agregarFotografo(new Fotografo(fotografo, "N/A", "0"));

                contador++;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return contador;
    }
}
