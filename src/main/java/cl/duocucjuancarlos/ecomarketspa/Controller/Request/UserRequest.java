package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//------------------aca se Recibe los datos necesarios para crear un nuevo usuario en el sistema----------------------------

public class UserRequest {
    private String run;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
