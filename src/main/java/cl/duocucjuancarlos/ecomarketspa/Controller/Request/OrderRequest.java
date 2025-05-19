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
        private Long userId; // esto es el id del carrito osea a cual usuario corresponde
        private List<Long> productIds; // esto es el carrito de compras con los id de los procutos que llevara

}//fin codigo


/*{
  "userId": 1,
  "productIds": [2, 3],
* */