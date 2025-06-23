package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.Data;

@Data
public class UserRequest {
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String password;
    private Integer rolId;
}