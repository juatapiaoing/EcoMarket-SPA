package cl.duocucjuancarlos.ecomarketspa.Controller.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String run;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
