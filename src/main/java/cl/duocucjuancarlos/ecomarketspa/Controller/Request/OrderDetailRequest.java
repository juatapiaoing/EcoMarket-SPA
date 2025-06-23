package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;

@Data
public class OrderDetailRequest {
    private Integer ordenId;
    private Integer stockId;
    private Integer cantidad;
    private Integer precioUnitario;
}