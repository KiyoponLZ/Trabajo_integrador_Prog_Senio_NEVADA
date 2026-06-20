package integrado.prog2.dao;

import integrado.prog2.config.ConexionDB;
import integrado.prog2.entities.DetallePedido;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Usuario;
import integrado.prog2.enums.Estado;
import integrado.prog2.enums.FormaPago;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO implements IBaseDAO<Pedido> {

    @Override
    public void crear(Pedido pedido) {
        String sqlPedido = "INSERT INTO pedido (fecha, estado, total, formaPago, usuario_id) VALUES (?, ?, ?, ?, ?)";
        String sqlDetalle = "INSERT INTO detalle_pedido (cantidad, subtotal, producto_id, pedido_id) VALUES (?, ?, ?, ?)";
        Connection conn = null;

        try {
            conn = ConexionDB.getConexion();
            conn.setAutoCommit(false); // Transacción manual

            try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS)) {
                pstmtPedido.setDate(1, java.sql.Date.valueOf(pedido.getFecha()));
                pstmtPedido.setString(2, pedido.getEstado().name());
                pstmtPedido.setDouble(3, pedido.getTotal());
                pstmtPedido.setString(4, pedido.getFormaPago().name());
                pstmtPedido.setLong(5, pedido.getUsuario().getId());
                pstmtPedido.executeUpdate();

                try (ResultSet rs = pstmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        pedido.setId(rs.getLong(1));
                    }
                }
            }

            try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)) {
                for (DetallePedido dp : pedido.getDetalles()) {
                    pstmtDetalle.setInt(1, dp.getCantidad());
                    pstmtDetalle.setDouble(2, dp.getSubtotal());
                    pstmtDetalle.setLong(3, dp.getProducto().getId());
                    pstmtDetalle.setLong(4, pedido.getId());
                    pstmtDetalle.executeUpdate();
                }
            }

            conn.commit(); // Confirmamos todo
            System.out.println("¡Pedido registrado con éxito en la base de datos!");

        } catch (SQLException e) {
            System.out.println("Error al guardar. Se cancelará toda la operación: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("Error al hacer rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Error al cerrar conexión: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public List<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE eliminado = false";

        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Pedido ped = new Pedido();
                ped.setId(rs.getLong("id"));
                ped.setFecha(rs.getDate("fecha").toLocalDate());
                ped.setEstado(Estado.valueOf(rs.getString("estado")));
                ped.setTotal(rs.getDouble("total"));
                ped.setFormaPago(FormaPago.valueOf(rs.getString("formaPago")));

                Usuario usu = new Usuario();
                usu.setId(rs.getLong("usuario_id"));
                ped.setUsuario(usu);

                lista.add(ped);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar pedidos: " + e.getMessage());
        }
        return lista;
    }

    @Override
    public void modificar(Pedido pedido) {
        // Generalmente solo modificamos el estado del pedido
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, pedido.getEstado().name());
            pstmt.setLong(2, pedido.getId());
            pstmt.executeUpdate();
            System.out.println("Estado del pedido actualizado a: " + pedido.getEstado().name());

        } catch (SQLException e) {
            System.out.println("Error al modificar el pedido: " + e.getMessage());
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "UPDATE pedido SET eliminado = true WHERE id = ?";
        try (Connection conn = ConexionDB.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            System.out.println("Pedido eliminado del sistema (baja lógica).");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el pedido: " + e.getMessage());
        }
    }
}