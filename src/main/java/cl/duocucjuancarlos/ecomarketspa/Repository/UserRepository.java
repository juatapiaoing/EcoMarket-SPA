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

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new UserResponse(1,
                "12345678-9"
                ,"Juan Carlos"
                ,"Tapia"
                ,"jua.tapiao@duocuc.cl"
                ,"123456789"));
    }
    public List<UserResponse> getAllUsers() {
        return users;
    }

    public UserResponse getUser(int userId) {
        for (UserResponse user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public UserResponse addUser(UserRequest userRequest) {
        int newId = users.size() + 1;
        users.add(new UserResponse(newId,
                userRequest.getRun(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail(),
                userRequest.getPhone()));

        return users.get(newId - 1);

    }

    public UserResponse updateUser(int index, UserRequest updatedUser) {
        UserResponse user = users.get(index - 1);

        if (updatedUser.getRun() != null) {
            user.setRun(updatedUser.getRun());
        }
        if (updatedUser.getFirstName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            user.setPhone(updatedUser.getPhone());
        }
        return user;
    }

    public UserResponse deleteUser(int userId) {
        int idDelete = userId - 1;
        if (idDelete < 0 || idDelete >= users.size()) {
            return null;
        }
        return users.remove(idDelete);
    }
}
