package modelo;

public abstract class Persona {
    protected String nombre;
    protected String identificacion;
    protected int contacto;

    public Persona(String nombre, String identificacion, int contacto) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.contacto = contacto;
    }

    protected Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public int getContacto() {
        return contacto;
    }

    public void setContacto(int contacto) {
        this.contacto = contacto;
    }

    protected abstract void mostrarInformacion();
}
