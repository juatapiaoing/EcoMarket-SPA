package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Role {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rol_id")
    private Integer id;

    @Column(nullable = false)
    private String tipoRol;

    @OneToMany(mappedBy = "rol")
    private List<User> usuarios;
}