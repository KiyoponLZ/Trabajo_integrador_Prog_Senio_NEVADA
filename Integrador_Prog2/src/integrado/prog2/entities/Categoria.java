package integrado.prog2.entities;

public class Categoria extends Base {
    private String nombre;
    private String descripcion;

    public Categoria() {}

    public Categoria(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    // El toString nos va a servir un montón para la consola después
    @Override
    public String toString() {
        return "Categoría [ID=" + getId() + ", Nombre=" + nombre + ", Descripcion=" + descripcion + "]";
    }
}