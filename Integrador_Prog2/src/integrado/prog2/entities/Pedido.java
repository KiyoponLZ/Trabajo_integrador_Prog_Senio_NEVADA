package integrado.prog2.entities;

import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Implementa Calculable para obligarnos a usar el método calcularTotal()
public class Pedido extends Base implements Calculable {
    private LocalDate fecha;
    private Estado estado;
    private Double total;
    private FormaPago formaPago;
    private Usuario usuario; // Relación con el usuario que compra
    private List<DetallePedido> detalles; // Composición: Un pedido tiene muchos detalles

    public Pedido() {
        // Inicializamos la lista vacía en el constructor para evitar errores de NullPointer
        this.detalles = new ArrayList<>();
        this.total = 0.0;
        this.fecha = LocalDate.now();
    }

    // --- Métodos propios exigidos por el UML ---

    // Agrega un detalle y actualiza el total automáticamente
    public void addDetallePedido(int cantidad, Double precio, Producto producto) {
        Double subtotal = cantidad * precio;
        DetallePedido nuevoDetalle = new DetallePedido(cantidad, subtotal, producto);
        this.detalles.add(nuevoDetalle);
        calcularTotal(); // Recalcula el total del pedido al agregar algo nuevo
    }

    // Busca si un producto ya está en el detalle
    public DetallePedido findeDetallePedidoByProducto(Producto producto) {
        for (DetallePedido dp : detalles) {
            if (dp.getProducto().getId().equals(producto.getId())) {
                return dp;
            }
        }
        return null;
    }

    // Elimina un detalle buscando por el producto
    public void deleteDetallePedidoByProducto(Producto producto) {
        DetallePedido detalleAEliminar = findeDetallePedidoByProducto(producto);
        if (detalleAEliminar != null) {
            this.detalles.remove(detalleAEliminar);
            calcularTotal(); // Recalcula el total tras eliminar
        }
    }

    // --- Implementación de la Interfaz Calculable ---
    @Override
    public Double calcularTotal() {
        Double suma = 0.0;
        for (DetallePedido dp : detalles) {
            suma += dp.getSubtotal();
        }
        this.total = suma;
        return this.total;
    }

    // Generar Getters y Setters con IntelliJ (fecha, estado, total, formaPago, usuario, detalles)
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }
    public FormaPago getFormaPago() { return formaPago; }
    public void setFormaPago(FormaPago formaPago) { this.formaPago = formaPago; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public List<DetallePedido> getDetalles() { return detalles; }
    public void setDetalles(List<DetallePedido> detalles) { this.detalles = detalles; }
}