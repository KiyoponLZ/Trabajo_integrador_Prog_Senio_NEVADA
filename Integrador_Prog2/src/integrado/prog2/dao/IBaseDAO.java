package integrado.prog2.dao;

import java.util.List;

// La <T> significa que es genérica. Después la reemplazaremos por Categoria, Producto, etc.
public interface IBaseDAO<T> {

    // Método para guardar un nuevo registro (CREATE)
    void crear(T entidad);

    // Método para traer todos los registros no eliminados (READ)
    List<T> listar();

    // Método para actualizar un registro existente (UPDATE)
    void modificar(T entidad);

    // Método para hacer la baja lógica usando el ID (DELETE)
    void eliminar(Long id);
}