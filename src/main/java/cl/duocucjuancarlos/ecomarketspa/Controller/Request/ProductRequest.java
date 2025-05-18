package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String productDescription;
}
