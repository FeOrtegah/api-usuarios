package usuario.usuarios.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import usuario.usuarios.models.Rol;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private String username;
    private String password;
    private String nombre;
    private String apellido;
    private String email;
    private Rol rol;
    private Long perfilId;
    private String calle;
    private String numero;
    private String ciudad;
    private String region;
    private String pais;
    private String codigoPostal;
    private String telefono;
}