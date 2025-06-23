package cl.duocucjuancarlos.ecomarketspa.Controller.Response;
import lombok.Data;

@Data
public class ProductResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private String tipoProducto;
    // Puedes agregar campos de stock si quieres mostrarlo en la respuesta
}