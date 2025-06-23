package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detalle-orden")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @PostMapping
    public ResponseEntity<OrderDetailResponse> create(@RequestBody OrderDetailRequest request) {
        return ResponseEntity.ok(orderDetailService.createOrderDetail(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(orderDetailService.getOrderDetailById(id));
    }

    @GetMapping
    public ResponseEntity<List<OrderDetailResponse>> getAll() {
        return ResponseEntity.ok(orderDetailService.getAllOrderDetails());
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailResponse> update(@PathVariable Integer id, @RequestBody OrderDetailRequest request) {
        return ResponseEntity.ok(orderDetailService.updateOrderDetail(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        orderDetailService.deleteOrderDetail(id);
        return ResponseEntity.noContent().build();
    }
}