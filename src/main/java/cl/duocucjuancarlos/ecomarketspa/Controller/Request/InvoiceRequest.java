package cl.duocucjuancarlos.ecomarketspa.Controller.Request;
import lombok.Data;

@Data
public class InvoiceRequest {
    private Integer usuarioId;
    private Integer ordenId;
    private Integer totalPedido;
}