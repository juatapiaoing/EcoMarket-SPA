package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private List<UserResponse> users;

    public UserRepository() {
        users = new ArrayList<>();
        users.add(new UserResponse(1
                ,"Juan Carlos"
                ,"Tapia"
                ,"jua.tapiao@duocuc.cl"
                ,"937459464"));
    }

    public List<UserResponse> getAllUsers() {
        return users;
    }
}
