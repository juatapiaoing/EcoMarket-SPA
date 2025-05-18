package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return userService.findAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable int userId){
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PostMapping("/addUser")
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest){
        UserResponse found = userService.addNewUser(userRequest);
        if (found != null){
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable int userId, @RequestBody UserRequest userRequest){
        UserResponse found = userService.updateUserById(userId, userRequest);
        if (found != null){
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable int userId){
        UserResponse found = userService.deleteUserById(userId);
        if (found != null){
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.badRequest().build();
    }
}
