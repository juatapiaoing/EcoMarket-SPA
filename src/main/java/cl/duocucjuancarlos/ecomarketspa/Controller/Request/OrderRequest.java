package cl.duocucjuancarlos.ecomarketspa.Controller.Request;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//                      Aca Recibe los datos necesarios para generar un pedido (order) del cliente.

public class OrderRequest {//inicio codigo
        private List<Integer> productId = new ArrayList<>(); // esto es el carrito de compras con los id de los procutos que llevara

}//fin codigo


/*{
  "userId": 1,
  "productIds": [2, 3],
* */