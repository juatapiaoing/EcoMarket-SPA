package cl.duocucjuancarlos.ecomarketspa.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "invoice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // RUN del usuario facturado
    @Column(nullable = false)
    private String run;

    // Nombre del usuario facturado
    @Column(nullable = false)
    private String name;

    // Lista de nombres de productos
    @ElementCollection
    @CollectionTable(name = "invoice_products", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "product_name")
    private List<String> products;

    // Lista de precios de los productos
    @ElementCollection
    @CollectionTable(name = "invoice_prices", joinColumns = @JoinColumn(name = "invoice_id"))
    @Column(name = "price")
    private List<Integer> prices;

    @Column(nullable = false)
    private Integer total;
}
