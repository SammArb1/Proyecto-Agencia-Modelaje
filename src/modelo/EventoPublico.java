package modelo;

/**
 * Evento abierto al público general.
 */
public class EventoPublico extends Evento {
    private int capacidadAsistentes;
    private String patrocinador;

    public EventoPublico(String nombreEvento, String fecha, Lugar[] lugar, Modelo[] modelo, Fotografo[] fotografo,
            int capacidadAsistentes, String patrocinador) {
        super(nombreEvento, fecha, lugar, modelo, fotografo);
        this.capacidadAsistentes = capacidadAsistentes;
        this.patrocinador = patrocinador;
    }

    public int getCapacidadAsistentes() {
        return capacidadAsistentes;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    @Override
    public String mostrarDetalles() {
        return "Evento Público: " + nombreEvento +
                "\nFecha: " + fecha +
                "\nLugar: " + getLugar() +
                "\nPatrocinador: " + patrocinador +
                "\nCapacidad Asistentes: " + capacidadAsistentes +
                "\nTotal Modelos: " + cantidadModelos +
                "\nTotal Fotógrafos: " + cantidadFotografos;
    }

    @Override
    public String tipoEvento() {
        return "Público";
    }
}
