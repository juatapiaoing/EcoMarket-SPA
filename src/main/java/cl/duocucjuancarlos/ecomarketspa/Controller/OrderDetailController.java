package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/detalle-orden")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody OrderDetailRequest request) {
        if (request == null || request.getOrdenId() == null) {
            // Mensaje de error corregido
            return ResponseEntity.badRequest().body(Map.of("error", "El ID de la orden es obligatorio"));
        }
        OrderDetailResponse newDetail = orderDetailService.createOrderDetail(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDetail);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de detalle de orden inválido"));
        }
        OrderDetailResponse detail = orderDetailService.getOrderDetailById(id);
        if (detail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Detalle de orden no encontrado"));
        }
        return ResponseEntity.ok(detail);
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponse>> getAll() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody OrderDetailRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de detalle de orden inválido"));
        }
        OrderDetailResponse updatedDetail = orderDetailService.updateOrderDetail(id, request);
        return ResponseEntity.ok(updatedDetail);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}