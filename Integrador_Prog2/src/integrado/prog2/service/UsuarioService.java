package integrado.prog2.service;

import integrado.prog2.dao.UsuarioDAO;
import integrado.prog2.entities.Usuario;
import integrado.prog2.exceptions.EntityNotFoundException;
import java.util.List;

public class UsuarioService {
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public void crear(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            System.out.println("Error desde Service: El nombre del usuario no puede estar vacío.");
            return;
        }
        usuarioDAO.crear(usuario);
    }

    public List<Usuario> listar() {
        return usuarioDAO.listar();
    }

    public void modificar(Usuario usuario) {
        usuarioDAO.modificar(usuario);
    }

    public void eliminar(Long id) {
        usuarioDAO.eliminar(id);
    }

    // ACÁ USAMOS NUESTRA EXCEPCIÓN PROPIA (Punto 6)
    public Usuario buscarPorId(Long id) {
        Usuario u = usuarioDAO.buscarPorId(id);
        if (u == null) {
            throw new EntityNotFoundException("Usuario con ID " + id + " no existe o fue dado de baja.");
        }
        return u;
    }
}