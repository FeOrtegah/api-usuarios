package usuario.usuarios.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import usuario.usuarios.models.Usuario;
import usuario.usuarios.repository.UsuarioRepository;

@Service
@Transactional
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario guardarUsuario(Usuario usuario) {
        if (usuarioRepository.existsByCredencialesUsername(usuario.getCredenciales().getUsername())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        usuario.setFechaRegistro(LocalDateTime.now());
        usuario.setUltimaActualizacion(LocalDateTime.now());
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> obtenerPorUsername(String username) {
        return usuarioRepository.findByCredencialesUsername(username);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id).map(usuario -> {
            if (!usuario.getCredenciales().getUsername().equals(usuarioActualizado.getCredenciales().getUsername()) &&
                usuarioRepository.existsByCredencialesUsername(usuarioActualizado.getCredenciales().getUsername())) {
                throw new IllegalArgumentException("El nombre de usuario ya existe");
            }
            usuario.setNombre(usuarioActualizado.getNombre());
            usuario.setApellido(usuarioActualizado.getApellido());
            usuario.setEmail(usuarioActualizado.getEmail());
            usuario.setRol(usuarioActualizado.getRol());
            usuario.setPerfil(usuarioActualizado.getPerfil());
            usuario.setDireccion(usuarioActualizado.getDireccion());
            usuario.setTelefono(usuarioActualizado.getTelefono());
            usuario.setUltimaActualizacion(LocalDateTime.now());
            return usuarioRepository.save(usuario);
        }).orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado");
        }
        usuarioRepository.deleteById(id);
    }
}
