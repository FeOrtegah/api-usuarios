package usuario.usuarios.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor

public class Credenciales {

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

}
