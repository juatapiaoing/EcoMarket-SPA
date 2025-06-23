package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "movimientos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Movimiento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movimiento_id")
    private Integer id;

    @Column(nullable = false)
    private String tipoMovimiento;

    @Column(nullable = false)
    private Integer cantidad;

    private String proveedor;
    private String ubicacion;

    @OneToMany(mappedBy = "movimiento")
    private List<Stock> stocks;
}