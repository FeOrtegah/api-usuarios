package usuario.usuarios.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import usuario.usuarios.dto.UsuarioDTO;
import usuario.usuarios.models.Credenciales;
import usuario.usuarios.models.Direccion;
import usuario.usuarios.models.Usuario;
import usuario.usuarios.service.UsuarioService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> crearUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.guardarUsuario(convertirDTOaEntidad(usuarioDTO)));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtener(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Usuario> buscarPorUsername(@RequestParam String username) {
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return usuarioService.obtenerPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = convertirDTOaEntidad(usuarioDTO);
            return ResponseEntity.ok(usuarioService.actualizarUsuario(id, usuario));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.eliminarUsuario(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Manejo global de errores de validación — devuelve 400 con lista de errores
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }

    private Usuario convertirDTOaEntidad(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setCredenciales(new Credenciales(dto.getUsername(), dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setRol(dto.getRol());
        usuario.setDireccion(new Direccion(
                dto.getCalle(), dto.getNumero(), dto.getCiudad(),
                dto.getRegion(), dto.getPais(), dto.getCodigoPostal()));
        usuario.setTelefono(dto.getTelefono());
        return usuario;
    }
}
