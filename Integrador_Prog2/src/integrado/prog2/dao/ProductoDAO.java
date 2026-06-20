package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Categoria;
import integrado.prog2.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO implements IBaseDAO<Producto> {

    @Override
    public void crear(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio, descripcion, stock, imagen, disponible, categoria_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getDescripcion());
            pstmt.setInt(4, producto.getStock());
            pstmt.setString(5, producto.getImagen());
            pstmt.setBoolean(6, producto.getDisponible());

            // Relación con Categoría
            pstmt.setLong(7, producto.getCategoria().getId());

            pstmt.executeUpdate();
            System.out.println("Producto '" + producto.getNombre() + "' creado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el producto: " + e.getMessage());
        }
    }

    @Override
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        // Hacemos un JOIN para traer el producto y los datos de su categoría al mismo tiempo
        String sql = "SELECT p.*, c.nombre AS categoria_nombre, c.descripcion AS categoria_desc " +
                "FROM producto p " +
                "INNER JOIN categoria c ON p.categoria_id = c.id " +
                "WHERE p.eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Producto prod = new Producto();
                prod.setId(rs.getLong("id"));
                prod.setNombre(rs.getString("nombre"));
                prod.setPrecio(rs.getDouble("precio"));
                prod.setDescripcion(rs.getString("descripcion"));
                prod.setStock(rs.getInt("stock"));
                prod.setImagen(rs.getString("imagen"));
                prod.setDisponible(rs.getBoolean("disponible"));

                // Armamos el objeto Categoria "al vuelo" con los datos que cruzamos en el JOIN
                Categoria cat = new Categoria();
                cat.setId(rs.getLong("categoria_id"));
                cat.setNombre(rs.getString("categoria_nombre"));
                cat.setDescripcion(rs.getString("categoria_desc"));

                // Enlazamos la categoría armada adentro de nuestro producto
                prod.setCategoria(cat);

                lista.add(prod);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void modificar(Producto producto) {
        String sql = "UPDATE producto SET nombre=?, precio=?, descripcion=?, stock=?, imagen=?, disponible=?, categoria_id=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setString(3, producto.getDescripcion());
            pstmt.setInt(4, producto.getStock());
            pstmt.setString(5, producto.getImagen());
            pstmt.setBoolean(6, producto.getDisponible());
            pstmt.setLong(7, producto.getCategoria().getId());
            pstmt.setLong(8, producto.getId()); // El ID va al final por el WHERE

            pstmt.executeUpdate();
            System.out.println("Producto actualizado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el producto: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        // Baja lógica: le cambiamos la etiqueta a true
        String sql = "UPDATE producto SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Producto eliminado del sistema (baja lógica).");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }
}