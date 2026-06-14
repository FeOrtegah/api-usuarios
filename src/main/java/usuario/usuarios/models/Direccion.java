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
public class Direccion {

    @Column(nullable = false)
    private String calle;
    @Column(nullable = false)
    private String numero;
    @Column(nullable = false)
    private String ciudad;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String pais;
    @Column(nullable = false)
    private String codigoPostal;
    
}
