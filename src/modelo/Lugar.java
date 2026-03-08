package modelo;

public class Lugar {
    private String nombre;
    private String direccion;
    private String ciudad;
    private int capacidad;
    private String tipoLugar;

    public Lugar(String nombre, String direccion, String ciudad, String tipoLugar, int capacidad) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.capacidad = capacidad;
        this.tipoLugar = tipoLugar;
    }

    /**
     * Constructor mínimo que permite instanciar un Lugar solo con su nombre.
     * Útil al cargar datos parciales desde los archivos de texto planos.
     */
    public Lugar(String nombreLugar) {
        this.nombre = nombreLugar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public String getTipoLugar() {
        return tipoLugar;
    }

    public void setTipoLugar(String tipoLugar) {
        this.tipoLugar = tipoLugar;
    }

    /**
     * Retorna la información básica del lugar.
     * Utilizado para alimentar las selecciones visuales del sistema.
     */
    @Override
    public String toString() {
        return nombre + " - " + ciudad;
    }
}
