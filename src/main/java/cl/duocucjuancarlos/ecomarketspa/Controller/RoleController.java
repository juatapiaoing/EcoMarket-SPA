package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody RoleRequest request) {
        if (request == null || request.getNombreRole() == null || request.getNombreRole().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El nombre del rol es obligatorio"));
        }
        RoleResponse newRole = roleService.createRole(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newRole);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "El ID del rol es inválido"));
        }
        RoleResponse role = roleService.getRoleById(id);
        if (role == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Rol no encontrado"));
        }
        return ResponseEntity.ok(role);
    }

    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody RoleRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "El ID del rol es inválido"));
        }
        RoleResponse updatedRole = roleService.updateRole(id, request);
        return ResponseEntity.ok(updatedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "El ID del rol es inválido"));
        }
        roleService.deleteRole(id);
        return ResponseEntity.noContent().build();
    }
}