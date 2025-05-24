package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;


    public List<UserResponse> findAllUsers() {
        return userRepository.getAllUsers();
    }


    public UserResponse getUserById(int userId) {
        if (userId >= 0 && userId < userRepository.getAllUsers().size()) {
            return userRepository.getUser(userId);
        }
        return null;
    }

    //falta logica de negocio
    public UserResponse addNewUser(UserRequest userRequest) {
        if (userRequest != null) {
            return userRepository.addUser(userRequest);
        }
        return null;
    }

    //falta logica de negocio
    public UserResponse updateUserById(int userId, UserRequest userRequest) {
        List<UserResponse>  List = userRepository.getAllUsers();
        if (userId >= 0 && userId < List.size() ) {
            return userRepository.updateUser(userId, userRequest);
        }
        return null;
    }

    public UserResponse deleteUserById(int userId) {
        if (userRepository.getUser(userId) == null) {
            return null;
        }
        return userRepository.deleteUser(userId);
    }
}
