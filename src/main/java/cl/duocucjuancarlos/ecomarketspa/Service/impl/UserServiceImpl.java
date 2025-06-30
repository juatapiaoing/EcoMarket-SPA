package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.UserRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Role;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.RoleRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    // Usamos inyección por constructor, la mejor práctica para las dependencias.
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Convierte una entidad User a un DTO UserResponse.
     * Es importante notar que accede a user.getRol().getNombreRole().
     * Por esto, en los tests, el objeto User mockeado DEBE tener un objeto Role adentro.
     */
    private UserResponse toResponse(User user) {
        UserResponse res = new UserResponse();
        res.setId(user.getId());
        res.setRun(user.getRun());
        res.setNombre(user.getNombre());
        res.setApellido(user.getApellido());
        res.setCorreo(user.getCorreo());
        res.setTelefono(user.getTelefono());
        res.setRol(user.getRol() != null ? user.getRol().getNombreRole() : null);
        return res;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        User user = new User();
        user.setRun(request.getRun());
        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setCorreo(request.getCorreo());
        user.setTelefono(request.getTelefono());
        user.setPassword(request.getPassword()); // Sin encriptación, como se solicitó

        // Buscamos el rol. Si no existe, lanzamos un error claro.
        // El test debe simular (mockear) esta llamada a roleRepository.findById.
        Role rol = roleRepository.findById(request.getRolId())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + request.getRolId()));
        user.setRol(rol);

        // Guardamos y luego convertimos a DTO para la respuesta.
        User savedUser = userRepository.save(user);
        return toResponse(savedUser);
    }

    @Override
    public UserResponse getUserById(Integer id) {
        // Buscamos el usuario. Si no se encuentra, orElse(null) devolverá null.
        // El controlador se encargará de transformar este null en un 404 Not Found.
        return userRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Integer id, UserRequest request) {
        // Para actualizar, primero debemos asegurar que el usuario exista.
        // Si findById no lo encuentra, orElseThrow lanzará la excepción, deteniendo la ejecución.
        // El test debe simular esta llamada.
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado para actualizar con ID: " + id));

        // Actualizamos los campos
        user.setRun(request.getRun());
        user.setNombre(request.getNombre());
        // ... (otros campos)

        if (request.getRolId() != null) {
            Role rol = roleRepository.findById(request.getRolId())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con ID: " + request.getRolId()));
            user.setRol(rol);
        }

        User updatedUser = userRepository.save(user);
        return toResponse(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) {
        // Es una buena práctica verificar que el recurso existe antes de intentar borrarlo.
        // Esto nos permite dar un mensaje de error consistente.
        // El test de borrado exitoso debe simular que existsById(id) devuelve true.
        // El test de borrado fallido debe simular que devuelve false.
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Usuario no encontrado con ID: " + id);
        }
        userRepository.deleteById(id);
    }
}