package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
    private int productId;
    private String productName;
    private String productDescription;
}
