package integrado.prog2.service;

import integrado.prog2.dao.PedidoDAO;
import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.entities.Pedido;
import integrado.prog2.entities.Usuario;
import integrado.prog2.exceptions.EntityNotFoundException;
import java.util.List;

public class PedidoService {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO(); // Lo usamos para validar al cliente

    public void crear(Pedido pedido) {
        // 1. VALIDACIÓN CRÍTICA: Verificar que el usuario exista y esté activo (Punto 3)
        Usuario u = usuarioDAO.buscarPorId(pedido.getUsuario().getId());
        if (u == null) {
            throw new EntityNotFoundException("No se puede procesar el pedido: El usuario con ID "
                    + pedido.getUsuario().getId() + " no existe o está de baja.");
        }

        // 2. VALIDACIÓN: Que el carrito no viaje vacío
        if (pedido.getDetalles() == null || pedido.getDetalles().isEmpty()) {
            System.out.println("Error desde Service: No se puede guardar un pedido sin productos.");
            return;
        }

        // Si pasa los controles, se lo manda al DAO para impactar la base de datos
        pedidoDAO.crear(pedido);
    }

    public List<Pedido> listar() {
        return pedidoDAO.listar();
    }

    public void modificar(Pedido pedido) {
        pedidoDAO.modificar(pedido);
    }

    public void eliminar(Long id) {
        pedidoDAO.eliminar(id);
    }
}