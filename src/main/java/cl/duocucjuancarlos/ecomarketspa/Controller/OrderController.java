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
    OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@RequestParam int id) {
        OrderResponse found = orderService.getOrderById(id);
        if(found != null) {
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{userId}/Order")
    public ResponseEntity<OrderResponse> createOrder(@PathVariable int userId, @RequestBody OrderRequest orderRequest) {
        if (orderRequest != null) {
            return ResponseEntity.ok(orderService.createOrder(userId, orderRequest));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{elementNumber}")
    public ResponseEntity<OrderResponse> updateOrder(@PathVariable int elementNumber, @RequestBody OrderRequest orderRequest) {
        if (orderRequest != null) {
        orderService.updateOrder(elementNumber, orderRequest);
        return ResponseEntity.ok(orderService.getOrderById(elementNumber));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{elementNumber}")
    public ResponseEntity<OrderResponse> deleteOrder(@PathVariable int elementNumber) {
        OrderResponse found = orderService.getOrderById(elementNumber);
        if(found != null) {
            orderService.deleteOrder(elementNumber);
            return ResponseEntity.ok(found);
        }
        return ResponseEntity.notFound().build();
    }

}
