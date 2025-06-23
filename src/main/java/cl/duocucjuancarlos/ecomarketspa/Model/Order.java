package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ordenes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orden_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    @Column(nullable = false)
    private String estado;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<OrderDetail> detalles;

    @OneToOne(mappedBy = "orden")
    private Invoice factura;
}