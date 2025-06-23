package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Integer usuarioId;
    private List<OrderDetailRequest> detalles;
    private String estado;
}

