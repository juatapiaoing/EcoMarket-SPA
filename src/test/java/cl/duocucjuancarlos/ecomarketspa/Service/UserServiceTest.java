package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(1, "1-9", "Juan", "Tapia", "mail@mail.com", "1234");
    }

    @Test
    void findAllUsers_returnsList() {
        when(userRepository.findAll()).thenReturn(List.of(testUser));
        List<UserResponse> result = userService.findAllUsers();
        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getFirstName());
    }

    @Test
    void findAllUsers_emptyList() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        List<UserResponse> result = userService.findAllUsers();
        assertTrue(result.isEmpty());
    }

    @Test
    void getUserById_found() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        UserResponse result = userService.getUserById(1);
        assertNotNull(result);
        assertEquals("Juan", result.getFirstName());
    }

    @Test
    void getUserById_notFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        UserResponse result = userService.getUserById(99);
        assertNull(result);
    }

    @Test
    void addNewUser_success() {
        UserRequest req = new UserRequest("11-1", "Ana", "Soto", "ana@mail.com", "5678");
        User expected = new User(2, req.getRun(), req.getFirstName(), req.getLastName(), req.getEmail(), req.getPhone());
        when(userRepository.save(any(User.class))).thenReturn(expected);

        UserResponse result = userService.addNewUser(req);

        assertNotNull(result);
        assertEquals("Ana", result.getFirstName());
        assertEquals("11-1", result.getRun());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User saved = captor.getValue();
        assertEquals("Ana", saved.getFirstName());
        assertEquals("11-1", saved.getRun());
    }

    @Test
    void addNewUser_nullRequest() {
        UserResponse result = userService.addNewUser(null);
        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserById_success() {
        UserRequest req = new UserRequest("1-9", "Juanito", "Tapia", "nuevo@mail.com", "9999");
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        UserResponse result = userService.updateUserById(1, req);

        assertNotNull(result);
        assertEquals("Juanito", result.getFirstName());
        assertEquals("nuevo@mail.com", result.getEmail());

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        User updated = captor.getValue();
        assertEquals("Juanito", updated.getFirstName());
        assertEquals("nuevo@mail.com", updated.getEmail());
    }

    @Test
    void updateUserById_userNotFound() {
        UserRequest req = new UserRequest("2-2", "Pedro", "Pérez", "no@mail.com", "8888");
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        UserResponse result = userService.updateUserById(2, req);
        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUserById_nullRequest() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        UserResponse result = userService.updateUserById(1, null);
        assertNull(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUserById_success() {
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        UserResponse result = userService.deleteUserById(1);
        assertNotNull(result);
        verify(userRepository).delete(testUser);
    }

    @Test
    void deleteUserById_notFound() {
        when(userRepository.findById(99)).thenReturn(Optional.empty());
        UserResponse result = userService.deleteUserById(99);
        assertNull(result);
        verify(userRepository, never()).delete(any());
    }
}