package modelo;

public class Fotografo extends Persona {

    private String especialidad;
    private int añosExperiencia;

    public Fotografo(String especialidad, int añosExperiencia) {
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }

    public Fotografo(String nombre, String identificacion, int contacto, String especialidad, int añosExperiencia) {
        super(nombre, identificacion, contacto);
        this.especialidad = especialidad;
        this.añosExperiencia = añosExperiencia;
    }

    public Fotografo(String nombre, String especialidad, String añosExperiencia) {
        this.nombre = nombre;
        this.especialidad = especialidad;
        // Constructor alternativo para recibir añosExperiencia como String.
        // Captura fallos de conversión para evitar excepciones críticas.
        try {
            this.añosExperiencia = Integer.parseInt(añosExperiencia);
        } catch (NumberFormatException e) {
            this.añosExperiencia = 0; // Se inicializa en 0 si el texto no es numérico
        }
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getAñosExperiencia() {
        return añosExperiencia;
    }

    public void setAñosExperiencia(int añosExperiencia) {
        this.añosExperiencia = añosExperiencia;
    }

    @Override
    protected void mostrarInformacion() {

    }

    /**
     * Genera una representación en formato texto del fotógrafo principal.
     * Diseñado para mostrarse en listas desplegables y tablas de la interfaz.
     */
    @Override
    public String toString() {
        if (nombre != null) {
            return nombre + " (" + especialidad + ")";
        }
        return especialidad;
    }
}
