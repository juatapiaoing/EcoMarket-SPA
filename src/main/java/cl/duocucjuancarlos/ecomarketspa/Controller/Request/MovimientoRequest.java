package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;

@Data
public class MovimientoRequest {
    private String tipoMovimiento;
    private Integer cantidad;
    private String proveedor;
    private String ubicacion;
}