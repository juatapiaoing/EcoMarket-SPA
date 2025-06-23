package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;

@Data
public class StockRequest {
    private Integer productoId;
    private Integer cantidadTotal;
    private Integer movimientoId; // Opcional, según el modelo
}