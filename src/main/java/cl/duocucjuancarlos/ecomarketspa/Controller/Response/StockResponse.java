package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.Data;

@Data
public class StockResponse {
    private Integer id;
    private Integer productoId;
    private String productoNombre;
    private Integer cantidadTotal;
    private Integer movimientoId; // Opcional
}