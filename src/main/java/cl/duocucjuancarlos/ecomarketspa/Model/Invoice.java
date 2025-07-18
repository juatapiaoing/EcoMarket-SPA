package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@Table(name = "facturas")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Invoice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "factura_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

    @OneToOne
    @JoinColumn(name = "orden_id")
    private Order orden;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaEmision;

    @Column(nullable = false)
    private Integer totalPedido;
}