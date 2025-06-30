package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Role;
import cl.duocucjuancarlos.ecomarketspa.Repository.RoleRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    private RoleResponse toResponse(Role role) {
        RoleResponse res = new RoleResponse();
        res.setId(role.getId());
        res.setNombreRole(role.getNombreRole());
        return res;
    }

    @Override
    @Transactional
    public RoleResponse createRole(RoleRequest request) {
        Role role = new Role();
        role.setNombreRole(request.getNombreRole());

        Role savedRole = roleRepository.save(role);
        return toResponse(savedRole);
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse getRoleById(Integer id) {
        return roleRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Integer id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + id));

        role.setNombreRole(request.getNombreRole());

        Role updatedRole = roleRepository.save(role);
        return toResponse(updatedRole);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id) {
        if (!roleRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Rol no encontrado con ID: " + id);
        }
        roleRepository.deleteById(id);
    }
}