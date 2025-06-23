package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.Data;

@Data
public class MovimientoResponse {
    private Integer id;
    private String tipoMovimiento;
    private Integer cantidad;
    private String proveedor;
    private String ubicacion;
}