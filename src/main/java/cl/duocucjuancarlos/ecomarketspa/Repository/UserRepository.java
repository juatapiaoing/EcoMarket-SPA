package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {//INICIO CODIGO
    private List<UserResponse> users;
    //---------------------------------------------------------------------------------------------------

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new UserResponse("12345678-9"
                ,"Juan Carlos"
                ,"Tapia"
                ,"jua.tapiao@duocuc.cl"
                ,"937459464"));
    }
    // Funcion para pedir un usuario (getMapping("/{elementNumber}"))
    public List<UserResponse> getAllUsers() {
        return users;
    }
    //---------------------------------------------------------------------------------------------------

    public UserResponse getUser(int userId) {
        return users.get(userId);
    }
    //---------------------------------------------------------------------------------------------------
    //Añadir usuario  @PostMapping("/add")
    public UserResponse addUser(UserRequest userRequest) {
        int newId = users.size() + 1;
        users.add(new UserResponse(userRequest.getRun(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPhone()));

        return users.get(newId);

    }
    //---------------------------------------------------------------------------------------------------
    // Modificar un usuario (@PutMapping("/{elementNumber}")
    public UserResponse updateUser(int index, UserRequest updatedUser) {
        if (index >= 0 && index < users.size()) {
            users.get(index).setRun(updatedUser.getRun());
            users.get(index).setFirstName(updatedUser.getFirstName());
            users.get(index).setLastName(updatedUser.getLastName());
            users.get(index).setEmail(updatedUser.getEmail());
            users.get(index).setPhone(updatedUser.getPhone());
            return users.get(index);
        }
        return null;
    }

    //Eliminar un usuario (@DeleteMapping("/{elementNumber}")
    public UserResponse deleteUser(int userId) {
        if (userId >= 0 && userId < users.size()) {
            return users.remove(userId);

        }
        return null;
    }


}//FIN CODIGO
