package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse extends InventoryRequest {
    private int id;
    private String name;
    private String description;
    private int quantity;
    private int price;
}

