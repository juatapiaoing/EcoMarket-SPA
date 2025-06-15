package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  //esto sirve para hacer funcionar el postman
@RequestMapping("/api/v1/orders")// esto es para llamarlo en el postman

public class OrderController {
    //---------------------------------------------------------------------------------------------
    // Crear un nuevo pedido
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable int orderId) {
        OrderResponse order = orderService.getOrderById(orderId);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<OrderResponse> addOrder(@PathVariable int userId, @RequestBody OrderRequest orderRequest) {
        OrderResponse order = orderService.addOrder(userId, orderRequest);
        if (order == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable int orderId, @RequestBody OrderRequest orderRequest) {
        OrderResponse order = orderService.updateOrder(orderId, orderRequest);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int orderId) {
        OrderResponse order = orderService.deleteOrder(orderId);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
