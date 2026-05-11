package usuario.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usuario.usuarios.models.Rol;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilDTO {
    private String nombre;
    private String descripcion;
    private Set<Rol> roles;
}