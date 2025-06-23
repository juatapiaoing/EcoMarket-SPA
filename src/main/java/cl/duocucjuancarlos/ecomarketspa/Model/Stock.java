package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stocks")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Stock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "producto_id")
    private Product producto;

    @Column(nullable = false)
    private Integer cantidadTotal;

    @ManyToOne
    @JoinColumn(name = "movimiento_id")
    private Movimiento movimiento;
}