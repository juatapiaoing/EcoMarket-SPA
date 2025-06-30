package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.MovimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {

    private final MovimientoService movimientoService;

    public MovimientoController(MovimientoService movimientoService) {
        this.movimientoService = movimientoService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MovimientoRequest request) {
        if (request == null || request.getTipoMovimiento() == null || request.getTipoMovimiento().trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El tipo de movimiento es obligatorio"));
        }
        MovimientoResponse newMovimiento = movimientoService.createMovimiento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newMovimiento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de movimiento inválido"));
        }
        MovimientoResponse movimiento = movimientoService.getMovimientoById(id);
        if (movimiento == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Movimiento no encontrado"));
        }
        return ResponseEntity.ok(movimiento);
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> getAll() {
        return ResponseEntity.ok(movimientoService.getAllMovimientos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody MovimientoRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de movimiento inválido"));
        }
        MovimientoResponse updatedMovimiento = movimientoService.updateMovimiento(id, request);
        return ResponseEntity.ok(updatedMovimiento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de movimiento inválido"));
        }
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}