package cl.duocucjuancarlos.ecomarketspa.Controller.Response;
import lombok.Data;

@Data
public class UserResponse {
    private Integer id;
    private String run;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private String rol; // Nombre del rol
}