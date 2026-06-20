package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO implements IBaseDAO<Categoria> {

    @Override
    public void crear(Categoria categoria) {
        String sql = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.executeUpdate();

            System.out.println("Categoría '" + categoria.getNombre() + "' creada con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al guardar la categoría: " + e.getMessage());
        }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> lista = new ArrayList<>();
        // El documento pide que solo se muestren las categorías no eliminadas
        String sql = "SELECT * FROM categoria WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            // rs.next() va fila por fila de los resultados que nos devolvió MySQL
            while (rs.next()) {
                Categoria cat = new Categoria();
                // Rescatamos los datos de las columnas y los metemos en el objeto Java
                cat.setId(rs.getLong("id"));
                cat.setNombre(rs.getString("nombre"));
                cat.setDescripcion(rs.getString("descripcion"));

                // Guardamos el objeto en nuestra lista
                lista.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar categorías: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void modificar(Categoria categoria) {
        String sql = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getDescripcion());
            pstmt.setLong(3, categoria.getId());
            pstmt.executeUpdate();

            System.out.println("Categoría actualizada con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al modificar la categoría: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        // Baja lógica: no borramos el registro, solo le cambiamos la etiqueta a true
        String sql = "UPDATE categoria SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

            System.out.println("Categoría eliminada del sistema (baja lógica).");

        } catch (SQLException e) {
            System.out.println("Error al eliminar la categoría: " + e.getMessage());
        }
    }
}