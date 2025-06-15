package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    UserResponse testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserResponse(1, "1-9", "Juan", "Tapia", "mail@mail.com", "1234");
    }

    @Test
    void getAllUsers_returnsList() throws Exception {
        when(userService.findAllUsers()).thenReturn(List.of(testUser));
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Juan"));
    }

    @Test
    void getUserById_found() throws Exception {
        when(userService.getUserById(1)).thenReturn(testUser);
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    void getUserById_notFound() throws Exception {
        when(userService.getUserById(99)).thenReturn(null);
        mockMvc.perform(get("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addUser_success() throws Exception {
        UserRequest request = new UserRequest("1-9", "Juan", "Tapia", "mail@mail.com", "1234");
        when(userService.addNewUser(any(UserRequest.class))).thenReturn(testUser);

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Juan"));
    }

    @Test
    void addUser_badRequest() throws Exception {
        when(userService.addNewUser(any(UserRequest.class))).thenReturn(null);
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateUser_success() throws Exception {
        UserRequest request = new UserRequest("1-9", "Juanito", "Tapia", "nuevo@mail.com", "9999");
        UserResponse updated = new UserResponse(1, "1-9", "Juanito", "Tapia", "nuevo@mail.com", "9999");
        when(userService.updateUserById(eq(1), any(UserRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Juanito"));
    }

    @Test
    void updateUser_notFound() throws Exception {
        when(userService.updateUserById(eq(99), any(UserRequest.class))).thenReturn(null);
        mockMvc.perform(put("/api/v1/users/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UserRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_success() throws Exception {
        when(userService.deleteUserById(1)).thenReturn(testUser);
        mockMvc.perform(delete("/api/v1/users/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteUser_notFound() throws Exception {
        when(userService.deleteUserById(99)).thenReturn(null);
        mockMvc.perform(delete("/api/v1/users/99"))
                .andExpect(status().isNotFound());
    }
}