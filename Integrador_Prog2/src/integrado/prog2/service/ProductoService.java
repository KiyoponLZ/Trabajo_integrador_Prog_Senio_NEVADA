package integrado.prog2.service;

import integrado.prog2.dao.ProductoDAO;
import integrado.prog2.entities.Producto;
import integrado.prog2.exceptions.EntityNotFoundException;
import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO = new ProductoDAO();

    public void crear(Producto producto) {
        // VALIDACIONES DE NEGOCIO (Punto 3)
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("Error desde Service: El nombre del producto no puede estar vacío.");
            return;
        }
        if (producto.getPrecio() < 0) {
            System.out.println("Error desde Service: El precio no puede ser negativo.");
            return;
        }
        if (producto.getStock() < 0) {
            System.out.println("Error desde Service: El stock no puede ser negativo.");
            return;
        }
        productoDAO.crear(producto);
    }

    public List<Producto> listar() {
        return productoDAO.listar();
    }

    public void modificar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            System.out.println("Error desde Service: El nombre no puede estar vacío al editar.");
            return;
        }
        if (producto.getPrecio() < 0 || producto.getStock() < 0) {
            System.out.println("Error desde Service: El precio o stock no pueden ser negativos.");
            return;
        }
        productoDAO.modificar(producto);
    }

    public void eliminar(Long id) {
        productoDAO.eliminar(id);
    }

    // ACÁ USAMOS NUESTRA EXCEPCIÓN PROPIA (Punto 6)
    public Producto buscarPorId(Long id) {
        Producto p = productoDAO.buscarPorId(id);
        if (p == null) {
            throw new EntityNotFoundException("Producto con ID " + id + " no existe en el sistema.");
        }
        return p;
    }
}