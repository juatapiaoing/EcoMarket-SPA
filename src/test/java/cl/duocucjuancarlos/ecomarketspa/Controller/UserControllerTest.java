package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private UserController userController;

    private UserRequest userRequest;
    private UserResponse userResponse;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
        userRequest.setNombre("Juan");
        userResponse = new UserResponse();
        userResponse.setId(1);
        userResponse.setNombre("Juan");
    }

    @Test
    void create_success() {
        given(userService.createUser(any(UserRequest.class))).willReturn(userResponse);
        ResponseEntity<UserResponse> response = userController.create(userRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userResponse, response.getBody());
    }

    @Test
    void getById_success() {
        given(userService.getUserById(1)).willReturn(userResponse);
        ResponseEntity<?> response = userController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getById_notFound() {
        given(userService.getUserById(99)).willReturn(null);
        ResponseEntity<?> response = userController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = userController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(userService.getAllUsers()).willReturn(Collections.singletonList(userResponse));
        ResponseEntity<List<UserResponse>> response = userController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void update_success() {
        given(userService.updateUser(eq(1), any(UserRequest.class))).willReturn(userResponse);
        ResponseEntity<?> response = userController.update(1, userRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = userController.update(0, userRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_success() {
        doNothing().when(userService).deleteUser(1);
        ResponseEntity<?> response = userController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteUser(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = userController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(userService).deleteUser(99);
        assertThrows(RuntimeException.class, () -> userController.delete(99));
    }
}