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


    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return (ResponseEntity<List<UserResponse>>) userRepository.getAllUsers();
    }


    public UserResponse getUserById(int userId) {
        return userRepository.getUser(userId);
    }

    public UserResponse addNewUser(UserRequest userRequest) {
        return userRepository.addUser(userRequest);
    }


    public UserResponse updateUserById(int userId, UserRequest userRequest) {
        return userRepository.updateUser(userId, userRequest);
    }

    public UserResponse deleteUserById(int userId) {
        return userRepository.deleteUser(userId);
    }
}
