package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;

@Data
public class ProductRequest {
    private String nombre;
    private String descripcion;
    private String tipoProducto;
    private Double precio;
    // No incluyas stock aquí; el stock se maneja aparte
}