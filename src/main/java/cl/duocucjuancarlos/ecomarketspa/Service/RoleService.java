package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.RoleRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.RoleResponse;

import java.util.List;

public interface RoleService {
    RoleResponse createRole(RoleRequest request);
    RoleResponse getRoleById(Integer id);
    List<RoleResponse> getAllRoles();
    RoleResponse updateRole(Integer id, RoleRequest request);
    void deleteRole(Integer id);
}