package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.Data;

@Data
public class OrderDetailResponse {
    private Integer id;
    private Integer stockId;
    private String productoNombre;
    private Integer cantidad;
    private Integer precioUnitario;
}