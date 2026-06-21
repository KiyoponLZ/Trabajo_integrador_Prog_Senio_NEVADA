package integrado.prog2.entities;

public class Producto extends Base {
    private String nombre;
    private Double precio;
    private String descripcion;
    private int stock;
    private String imagen;
    private Boolean disponible;
    private Categoria categoria; // Relación con Categoría

    public Producto() {}

    // Getters y Setters (Omito el código de los getters/setters por espacio, pero debes generarlos en IntelliJ con Alt+Insert)
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public String getImagen() { return imagen; }
    public void setImagen(String imagen) { this.imagen = imagen; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        // Operador ternario: Si la categoría no es nula, sacamos el nombre. Si es nula, ponemos un texto por defecto.
        String nombreCategoria = (this.categoria != null && this.categoria.getNombre() != null)
                ? this.categoria.getNombre()
                : "Sin categoría";

        return "Producto [ID=" + this.getId() + ", Nombre=" + this.nombre +
                ", Precio=$" + this.precio + ", Stock=" + this.stock +
                ", Categoría=" + nombreCategoria + "]";
    }
}