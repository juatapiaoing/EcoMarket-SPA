package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "detalle_orden")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderDetail {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detalle_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "orden_id")
    private Order orden;

    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer precioUnitario;
}