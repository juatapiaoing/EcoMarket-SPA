package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private static UserRepository userRepository;


    public static ResponseEntity<List<UserResponse>> findAllUsers() {
        return (ResponseEntity<List<UserResponse>>) userRepository.getAllUsers();
    }
}
