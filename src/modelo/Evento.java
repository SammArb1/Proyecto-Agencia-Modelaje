package modelo;

public abstract class Evento {
    protected String nombreEvento;
    protected String fecha;
    protected Lugar[] lugar; // Arreglo que almacena los lugares asignados al evento
    protected Modelo[] modelo; // Arreglo que contiene los modelos que participarán
    protected Fotografo[] fotografo; // Arreglo con los fotógrafos encargados de la cobertura

    // Contadores para llevar el registro de elementos actuales en cada arreglo
    protected int cantidadLugares = 0;
    protected int cantidadModelos = 0;
    protected int cantidadFotografos = 0;

    public Evento(String nombreEvento, String fecha, Lugar[] lugar, Modelo[] modelo, Fotografo[] fotografo) {
        this.nombreEvento = nombreEvento;
        this.fecha = fecha;
        // Asignación de arreglos recibidos
        this.lugar = lugar;
        this.modelo = modelo;
        this.fotografo = fotografo;

        // Calcular cantidades iniciales
        if (lugar != null) {
            for (Lugar l : lugar) {
                if (l != null)
                    cantidadLugares++;
            }
        } else {
            this.lugar = new Lugar[1];
        }

        if (modelo != null) {
            for (Modelo m : modelo) {
                if (m != null)
                    cantidadModelos++;
            }
        } else {
            this.modelo = new Modelo[50];
        }

        if (fotografo != null) {
            for (Fotografo f : fotografo) {
                if (f != null)
                    cantidadFotografos++;
            }
        } else {
            this.fotografo = new Fotografo[20];
        }
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLugar() {
        if (lugar != null && lugar.length > 0 && lugar[0] != null) {
            return lugar[0].getNombre();
        }
        return "Sin lugar asignado";
    }

    /**
     * Obtiene el lugar principal asignado al evento.
     * Útil para operaciones de persistencia que requieren un único lugar.
     */
    public Lugar getLugarObjeto() {
        if (lugar != null && lugar.length > 0)
            return lugar[0];
        return null;
    }

    public void setLugar(Lugar[] lugar) {
        this.lugar = lugar;
    }

    public Modelo[] getModelo() {
        return modelo;
    }

    public void setModelo(Modelo[] modelo) {
        this.modelo = modelo;
    }

    public Fotografo[] getFotografo() {
        return fotografo;
    }

    public void setFotografo(Fotografo[] fotografo) {
        this.fotografo = fotografo;
    }

    // ===================================
    // MÉTODOS DE OBTENCIÓN DE DATOS
    // ===================================

    /**
     * Retorna una cadena con los detalles completos del evento.
     * Este método debe ser implementado por las subclases.
     */
    public abstract String mostrarDetalles();

    /**
     * Devuelve el tipo de evento como cadena de texto.
     */
    public String tipoEvento() {
        return "Abstracto";
    }

    public int getNumModelos() {
        return cantidadModelos;
    }

    public int getNumFotografos() {
        return cantidadFotografos;
    }

    public boolean agregarModelo(Modelo m) {
        if (cantidadModelos < this.modelo.length) {
            this.modelo[cantidadModelos] = m;
            cantidadModelos++;
            return true;
        }
        return false;
    }

    public boolean agregarFotografo(Fotografo f) {
        if (cantidadFotografos < this.fotografo.length) {
            this.fotografo[cantidadFotografos] = f;
            cantidadFotografos++;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return nombreEvento + " (" + fecha + ") - " + tipoEvento();
    }
}
