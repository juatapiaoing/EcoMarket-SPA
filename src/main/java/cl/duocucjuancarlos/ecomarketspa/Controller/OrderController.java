package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ordenes")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderRequest request) {
        if (request == null || request.getUsuarioId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario es obligatorio"));
        }
        OrderResponse newOrder = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de orden inválido"));
        }
        OrderResponse order = orderService.getOrderById(id);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Orden no encontrada"));
        }
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody OrderRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de orden inválido"));
        }
        OrderResponse updatedOrder = orderService.updateOrder(id, request);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}