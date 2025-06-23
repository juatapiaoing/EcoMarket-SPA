package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.Data;
import java.util.Date;

@Data
public class InvoiceResponse {
    private Integer id;
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer ordenId;
    private Date fechaEmision;
    private Integer totalPedido;
}