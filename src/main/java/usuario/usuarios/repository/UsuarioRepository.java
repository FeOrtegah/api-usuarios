package usuario.usuarios.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import usuario.usuarios.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCredencialesUsername(String username);
    boolean existsByCredencialesUsername(String username);
}
