package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.MovimientoRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.MovimientoResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.MovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movimientos")
public class MovimientoController {
    @Autowired
    private MovimientoService movimientoService;

    @PostMapping
    public ResponseEntity<MovimientoResponse> create(@RequestBody MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.createMovimiento(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimientoResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(movimientoService.getMovimientoById(id));
    }

    @GetMapping
    public ResponseEntity<List<MovimientoResponse>> getAll() {
        return ResponseEntity.ok(movimientoService.getAllMovimientos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimientoResponse> update(@PathVariable Integer id, @RequestBody MovimientoRequest request) {
        return ResponseEntity.ok(movimientoService.updateMovimiento(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        movimientoService.deleteMovimiento(id);
        return ResponseEntity.noContent().build();
    }
}