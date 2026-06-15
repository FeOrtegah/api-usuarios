package usuario.usuarios.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usuario.usuarios.models.Rol;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {

    @NotBlank(message = "El nombre del perfil es obligatorio")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;

    @NotEmpty(message = "El perfil debe tener al menos un rol")
    private Set<Rol> roles;
}
