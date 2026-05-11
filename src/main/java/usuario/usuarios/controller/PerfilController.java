package usuario.usuarios.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import usuario.usuarios.models.Perfil;
import usuario.usuarios.service.PerfilService;
import usuario.usuarios.dto.PerfilDTO;


@RestController
@RequestMapping("/api/v1/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<Perfil> crearPerfil(@RequestBody PerfilDTO perfilDTO) {
        return ResponseEntity.ok(perfilService.guardarPerfil(convertirDTOaEntidad(perfilDTO)));
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listar() {
        return ResponseEntity.ok(perfilService.listarPerfiles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> obtenerPerfilPorId(@PathVariable Long id) {
        return perfilService.obtenerPerfilPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Perfil> obtenerPerfilPorNombre(@RequestParam String nombre) {
        return perfilService.obtenerPerfilPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> actualizarPerfil(@PathVariable Long id, @RequestBody PerfilDTO perfilDTO) {
        try {
            Perfil perfil = convertirDTOaEntidad(perfilDTO);
            return ResponseEntity.ok(perfilService.actualizarPerfil(id, perfil));
        } catch (IllegalArgumentException e) { // Cambiado _ por e
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        try {
            perfilService.eliminarPerfil(id);
            return ResponseEntity.noContent().build(); // Cambiado a noContent para éxito
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Perfil convertirDTOaEntidad(PerfilDTO dto) {
        Perfil perfil = new Perfil();
        perfil.setNombre(dto.getNombre());
        perfil.setDescripcion(dto.getDescripcion());
        perfil.setRoles(dto.getRoles());
        return perfil;
    }
}