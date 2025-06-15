package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class OrderService {//inicio codigo
//---------------------------------------------------------------------------------------------
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;

    private OrderResponse toResponse(Order order) {
        return new OrderResponse(order.getUser().getId(), order.getProductId());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public OrderResponse getOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.map(this::toResponse).orElse(null);
    }

    public OrderResponse addOrder(int userId, OrderRequest orderRequest) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return null;
        Order order = new Order(null, userOpt.get(), orderRequest.getProductId());
        order = orderRepository.save(order);
        return toResponse(order);
    }

    public OrderResponse updateOrder(int orderId, OrderRequest orderRequest) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty() || orderRequest == null) return null;
        Order order = optionalOrder.get();
        order.setProductId(orderRequest.getProductId());
        order = orderRepository.save(order);
        return toResponse(order);
    }

    public OrderResponse deleteOrder(int orderId) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isEmpty()) return null;
        Order order = optionalOrder.get();
        orderRepository.delete(order);
        return toResponse(order);
    }
//---------------------------------------------------------------------------------------------

}
