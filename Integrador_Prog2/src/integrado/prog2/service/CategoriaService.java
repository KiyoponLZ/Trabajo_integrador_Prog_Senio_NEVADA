package integrado.prog2.service;

import integrado.prog2.dao.CategoriaDAO;
import integrado.prog2.entities.Categoria;
import java.util.List;

public class CategoriaService {

    // El servicio tiene una instancia del DAO para comunicarse con la base de datos
    private final CategoriaDAO categoriaDAO = new CategoriaDAO();

    public void crear(Categoria categoria) {
        // VALIDACIÓN DE NEGOCIO (Punto 3 del profe)
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("Error desde Service: El nombre de la categoría no puede estar vacío.");
            return;
        }

        // Si pasa la validación, va al DAO
        categoriaDAO.crear(categoria);
    }

    public List<Categoria> listar() {
        return categoriaDAO.listar();
    }

    public void modificar(Categoria categoria) {
        // VALIDACIÓN DE NEGOCIO (Punto 3 del profe)
        if (categoria.getNombre() == null || categoria.getNombre().trim().isEmpty()) {
            System.out.println("Error desde Service: El nuevo nombre no puede estar vacío.");
            return;
        }

        categoriaDAO.modificar(categoria);
    }

    public void eliminar(Long id) {
        categoriaDAO.eliminar(id);
    }
}