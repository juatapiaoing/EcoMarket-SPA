package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Role;
import cl.duocucjuancarlos.ecomarketspa.Repository.RoleRepository;
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

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleRequest roleRequest;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(1);
        role.setNombreRole("ADMIN");

        roleRequest = new RoleRequest();
        roleRequest.setNombreRole("ADMIN");
    }

    @Test
    void createRole() {
        given(roleRepository.save(any(Role.class))).willReturn(role);
        RoleResponse response = roleService.createRole(roleRequest);
        assertNotNull(response);
        assertEquals("ADMIN", response.getNombreRole());
    }

    @Test
    void getRoleById_success() {
        given(roleRepository.findById(1)).willReturn(Optional.of(role));
        RoleResponse response = roleService.getRoleById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void getRoleById_notFound() {
        given(roleRepository.findById(99)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> roleService.getRoleById(99));
    }
}