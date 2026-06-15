package usuario.usuarios.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import usuario.usuarios.models.Perfil;
import usuario.usuarios.service.PerfilService;
import usuario.usuarios.dto.PerfilDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/perfiles")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @PostMapping
    public ResponseEntity<Perfil> crearPerfil(@Valid @RequestBody PerfilDTO perfilDTO) {
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
        if (nombre == null || nombre.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return perfilService.obtenerPerfilPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Perfil> actualizarPerfil(@PathVariable Long id, @Valid @RequestBody PerfilDTO perfilDTO) {
        try {
            Perfil perfil = convertirDTOaEntidad(perfilDTO);
            return ResponseEntity.ok(perfilService.actualizarPerfil(id, perfil));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPerfil(@PathVariable Long id) {
        try {
            perfilService.eliminarPerfil(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    private Perfil convertirDTOaEntidad(PerfilDTO dto) {
        Perfil perfil = new Perfil();
        perfil.setNombre(dto.getNombre());
        perfil.setDescripcion(dto.getDescripcion());
        perfil.setRoles(dto.getRoles());
        return perfil;
    }
}
