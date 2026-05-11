package usuario.usuarios.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import usuario.usuarios.models.Perfil;
import usuario.usuarios.repository.PerfilRepository;

@Service
@Transactional
public class PerfilService {
     
    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Perfil guardarPerfil(Perfil perfil) {
        if (perfilRepository.existsByNombre(perfil.getNombre())) {
            throw new IllegalArgumentException("El nombre del perfil ya existe");
        }
        return perfilRepository.save(perfil);
    }

    public List<Perfil> listarPerfiles() {
        return perfilRepository.findAll();
    }

    public Optional<Perfil> obtenerPerfilPorId(Long id) {
        return perfilRepository.findById(id);
    }

    public Optional<Perfil> obtenerPerfilPorNombre(String nombre) {
        return perfilRepository.findByNombre(nombre);
    }

    public Perfil actualizarPerfil(Long id, Perfil perfilActualizado) {
        return perfilRepository.findById(id).map(perfil -> {
            if (!perfil.getNombre().equals(perfilActualizado.getNombre()) &&
                perfilRepository.existsByNombre(perfilActualizado.getNombre())) {
                throw new IllegalArgumentException("El nombre del perfil ya existe");
            }
            perfil.setNombre(perfilActualizado.getNombre());
            perfil.setRoles(perfilActualizado.getRoles());
            return perfilRepository.save(perfil);
        }).orElseThrow(() -> new IllegalArgumentException("Perfil no encontrado"));
    }

    public void eliminarPerfil(Long id) {
        if (!perfilRepository.existsById(id)) {
            throw new IllegalArgumentException("Perfil no encontrado");
        }
        perfilRepository.deleteById(id);
    }
}

