package modelo;

/**
 * Evento exclusivo para clientes VIP o marcas importantes.
 */
public class EventoPrivado extends Evento {
    private String cliente;
    private char confidencialidad; // e.g., 'A', 'M', 'B'

    public EventoPrivado(String nombreEvento, String fecha, Lugar[] lugar, Modelo[] modelo, Fotografo[] fotografo,
            String cliente, char confidencialidad) {
        super(nombreEvento, fecha, lugar, modelo, fotografo);
        this.cliente = cliente;
        this.confidencialidad = confidencialidad;
    }

    public String getCliente() {
        return cliente;
    }

    public char getConfidencialidad() {
        return confidencialidad;
    }

    @Override
    public String mostrarDetalles() {
        return "Evento Privado: " + nombreEvento +
                "\nFecha: " + fecha +
                "\nLugar: " + getLugar() +
                "\nCliente: " + cliente +
                "\nConfidencialidad: " + confidencialidad +
                "\nTotal Modelos: " + cantidadModelos +
                "\nTotal Fotógrafos: " + cantidadFotografos;
    }

    @Override
    public String tipoEvento() {
        return "Privado";
    }
}
