package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Role;
import cl.duocucjuancarlos.ecomarketspa.Repository.RoleRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private RoleResponse toResponse(Role role) {
        RoleResponse res = new RoleResponse();
        res.setId(role.getId());
        res.setTipoRol(role.getTipoRol());
        return res;
    }

    @Override
    public RoleResponse createRole(RoleRequest request) {
        Role role = new Role();
        role.setTipoRol(request.getTipoRol());
        return toResponse(roleRepository.save(role));
    }

    @Override
    public RoleResponse getRoleById(Integer id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        return toResponse(role);
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse updateRole(Integer id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        role.setTipoRol(request.getTipoRol());
        return toResponse(roleRepository.save(role));
    }

    @Override
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}