package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuario_id")
    private Integer id;

    @Column(nullable = false, unique = true)
    private String run;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String correo;

    private String telefono;
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id")
    private Role rol;

    @OneToMany(mappedBy = "usuario")
    private List<Order> ordenes;

    @OneToMany(mappedBy = "usuario")
    private List<Invoice> facturas;
}