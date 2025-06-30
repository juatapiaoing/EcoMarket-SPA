package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Role;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.RoleRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private Role role;
    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        // 1. Preparamos los modelos (entidades) que simularán estar en la BD
        role = new Role();
        role.setId(1);
        role.setNombreRole("ADMIN");

        user = new User();
        user.setId(1);
        user.setNombre("Juan");
        user.setRol(role); // <-- Enlazamos el rol al usuario

        // 2. Preparamos el objeto de solicitud que vendría del controlador
        userRequest = new UserRequest();
        userRequest.setNombre("Juan");
        userRequest.setRolId(1);
        userRequest.setPassword("password");
    }

    @Test
    void createUserTest() {
        // Given: Configuramos los mocks que el método 'createUser' necesita
        // - Cuando se busque un rol con ID 1, se debe encontrar nuestro rol mock.
        given(roleRepository.findById(1)).willReturn(Optional.of(role));
        // - Cuando se guarde cualquier objeto User, se debe devolver nuestro usuario mock (con el rol ya enlazado).
        given(userRepository.save(any(User.class))).willReturn(user);

        // When: Ejecutamos el método del servicio
        UserResponse response = userService.createUser(userRequest);

        // Then: Verificamos que el resultado es el esperado
        assertNotNull(response);
        assertEquals("Juan", response.getNombre());
        assertEquals("ADMIN", response.getRol()); // <-- Esta aserción ahora pasará
    }

    @Test
    void getUserByIdTest() {
        // Given: Cuando se busque un usuario con ID 1, se debe encontrar nuestro usuario mock.
        given(userRepository.findById(1)).willReturn(Optional.of(user));

        // When
        UserResponse response = userService.getUserById(1);

        // Then
        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("ADMIN", response.getRol()); // <-- Esta aserción ahora pasará
    }

    @Test
    void getUserById_throwsException_whenNotFound() {
        // Given: Cuando se busque un ID que no existe, el repositorio devuelve un Optional vacío.
        given(userRepository.findById(99)).willReturn(Optional.empty());

        // When & Then: Verificamos que se lanza la excepción correcta.
        assertThrows(RuntimeException.class, () -> {
            userService.getUserById(99);
        });
    }

    @Test
    void updateUserTest() {
        // Given: Configuramos todos los mocks que 'updateUser' necesita
        // - Debe encontrar al usuario que se va a actualizar.
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        // - Debe encontrar el rol que se le va a asignar.
        given(roleRepository.findById(1)).willReturn(Optional.of(role));
        // - Cuando se guarde, debe devolver el usuario actualizado.
        given(userRepository.save(any(User.class))).willReturn(user);

        // When
        UserResponse response = userService.updateUser(1, userRequest);

        // Then
        assertNotNull(response);
        assertEquals("ADMIN", response.getRol()); // <-- Esta aserción ahora pasará
    }

    @Test
    void deleteUserTest() {
        // Given: Para que el borrado funcione, primero debe existir el usuario.
        given(userRepository.existsById(1)).willReturn(true);
        // 'deleteById' no devuelve nada, así que no se usa given.

        // When
        // 'assertDoesNotThrow' es una forma elegante de asegurar que no se lancen excepciones.
        assertDoesNotThrow(() -> userService.deleteUser(1));

        // Then: Verificamos que el método deleteById fue llamado exactamente una vez.
        verify(userRepository, times(1)).deleteById(1);
    }

    @Test

    void deleteUser_throwsException_whenNotFound() {
        // Given: Cuando se verifique la existencia, devolverá false.
        given(userRepository.existsById(99)).willReturn(false);

        // When & Then: Verificamos que se lanza la excepción esperada.
        assertThrows(RuntimeException.class, () -> {
            userService.deleteUser(99);
        });
    }
}