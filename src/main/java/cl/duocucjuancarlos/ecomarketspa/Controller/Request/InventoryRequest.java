package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

//            Aca Recibe información para ingresar productos al inventario (cantidad,Nombre,Descripcion,Precio).

public class InventoryRequest {
    private String name;
    private String description;
    private int quantity;
    private int price;
}
