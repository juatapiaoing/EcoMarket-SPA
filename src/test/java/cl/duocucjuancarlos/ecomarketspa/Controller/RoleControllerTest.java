package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.RoleService;
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
class RoleControllerTest {

    @Mock
    private RoleService roleService;
    @InjectMocks
    private RoleController roleController;

    private RoleRequest roleRequest;
    private RoleResponse roleResponse;

    @BeforeEach
    void setUp() {
        roleRequest = new RoleRequest();
        roleRequest.setNombreRole("ADMIN");
        roleResponse = new RoleResponse();
        roleResponse.setId(1);
        roleResponse.setNombreRole("ADMIN");
    }

    @Test
    void create_success() {
        given(roleService.createRole(any(RoleRequest.class))).willReturn(roleResponse);
        ResponseEntity<?> response = roleController.create(roleRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(roleResponse, response.getBody());
    }

    @Test
    void create_badRequest_whenNameIsNull() {
        roleRequest.setNombreRole(null);
        ResponseEntity<?> response = roleController.create(roleRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_success() {
        given(roleService.getRoleById(1)).willReturn(roleResponse);
        ResponseEntity<?> response = roleController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getById_notFound() {
        given(roleService.getRoleById(99)).willReturn(null);
        ResponseEntity<?> response = roleController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = roleController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(roleService.getAllRoles()).willReturn(Collections.singletonList(roleResponse));
        ResponseEntity<List<RoleResponse>> response = roleController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void update_success() {
        given(roleService.updateRole(eq(1), any(RoleRequest.class))).willReturn(roleResponse);
        ResponseEntity<?> response = roleController.update(1, roleRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = roleController.update(-1, roleRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_throwsException_whenNotFound() {
        given(roleService.updateRole(eq(99), any(RoleRequest.class))).willThrow(new RuntimeException("No encontrado"));
        assertThrows(RuntimeException.class, () -> roleController.update(99, roleRequest));
    }

    @Test
    void delete_success() {
        doNothing().when(roleService).deleteRole(1);
        ResponseEntity<?> response = roleController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(roleService).deleteRole(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = roleController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(roleService).deleteRole(99);
        assertThrows(RuntimeException.class, () -> roleController.delete(99));
    }
}