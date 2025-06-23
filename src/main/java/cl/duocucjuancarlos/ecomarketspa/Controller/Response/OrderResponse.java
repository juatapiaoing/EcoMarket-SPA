package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class OrderResponse {
    private Integer id;
    private Integer usuarioId;
    private String usuarioNombre;
    private Date fechaCreacion;
    private String estado;
    private List<OrderDetailResponse> detalles;
}

