package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getRun(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone());
    }

    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserResponse getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(this::toResponse).orElse(null);
    }

    public UserResponse addNewUser(UserRequest userRequest) {
        if (userRequest == null) return null;
        User user = new User(null, userRequest.getRun(), userRequest.getFirstName(), userRequest.getLastName(), userRequest.getEmail(), userRequest.getPhone());
        user = userRepository.save(user);
        return toResponse(user);
    }

    public UserResponse updateUserById(int userId, UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty() || userRequest == null) return null;
        User user = optionalUser.get();
        if (userRequest.getRun() != null) user.setRun(userRequest.getRun());
        if (userRequest.getFirstName() != null) user.setFirstName(userRequest.getFirstName());
        if (userRequest.getLastName() != null) user.setLastName(userRequest.getLastName());
        if (userRequest.getEmail() != null) user.setEmail(userRequest.getEmail());
        if (userRequest.getPhone() != null) user.setPhone(userRequest.getPhone());
        user = userRepository.save(user);
        return toResponse(user);
    }

    public UserResponse deleteUserById(int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;
        User user = optionalUser.get();
        userRepository.delete(user);
        return toResponse(user);
    }
}
