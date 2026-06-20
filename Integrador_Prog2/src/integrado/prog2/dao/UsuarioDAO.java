package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Rol;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IBaseDAO<Usuario> {

    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuario (nombre, apellido, mail, celular, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getMail());
            pstmt.setString(4, usuario.getCelular());
            pstmt.setString(5, usuario.getContrasena());

            // TRUCO: Convertimos el Enum a texto para que MySQL lo entienda
            pstmt.setString(6, usuario.getRol().name());

            pstmt.executeUpdate();
            System.out.println("Usuario '" + usuario.getNombre() + "' creado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al guardar el usuario: " + e.getMessage());
        }
    }

    @Override
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Usuario usu = new Usuario();
                usu.setId(rs.getLong("id"));
                usu.setNombre(rs.getString("nombre"));
                usu.setApellido(rs.getString("apellido"));
                usu.setMail(rs.getString("mail"));
                usu.setCelular(rs.getString("celular"));
                usu.setContrasena(rs.getString("contraseña"));

                // TRUCO: Convertimos el texto de MySQL de vuelta a Enum
                usu.setRol(Rol.valueOf(rs.getString("rol")));

                lista.add(usu);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar usuarios: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void modificar(Usuario usuario) {
        String sql = "UPDATE usuario SET nombre=?, apellido=?, mail=?, celular=?, contrasena=?, rol=? WHERE id=?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getMail());
            pstmt.setString(4, usuario.getCelular());
            pstmt.setString(5, usuario.getContrasena());
            pstmt.setString(6, usuario.getRol().name());
            pstmt.setLong(7, usuario.getId());

            pstmt.executeUpdate();
            System.out.println("Usuario actualizado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE usuario SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado del sistema (baja lógica).");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }
}