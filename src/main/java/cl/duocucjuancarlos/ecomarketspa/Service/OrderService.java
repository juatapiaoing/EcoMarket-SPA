package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service

public class OrderService {//inicio codigo
//---------------------------------------------------------------------------------------------
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InventoryRepository inventoryRepository;



    public OrderResponse createOrder(int userId, OrderRequest request) {
        if (request == null || request.getProductId() == null || request.getProductId().isEmpty()) {
            return null;
        }

        List<UserResponse> users = userRepository.getAllUsers();
        boolean userExists = users.stream()
                .anyMatch(user -> user.getId() == userId);
        if (!userExists) {
            return null;
        }
        List<InventoryResponse> inventory = inventoryRepository.getInventoryResponses();

        Set<Integer> validProductId = inventory.stream()
                .map(InventoryResponse::getId)
                .collect(Collectors.toSet());

        for (Integer id : request.getProductId()) {
            if (!validProductId.contains(id)) {
                return null;
            }
        }
        return orderRepository.addOrder(userId, request);
    }

    public OrderResponse updateOrder(int orderId, OrderRequest request) {
        List<UserResponse> users = userRepository.getAllUsers();
        List<OrderResponse> orders = orderRepository.getAllOrders();
        if (request == null) {
            return null;
        }
        for (OrderResponse order : orders) {
            if(order.getUserId() == orderId){
                return orderRepository.updateOrder(orderId, request);
            }
        }
        return null;
    }
    
    public OrderResponse deleteOrder(int orderId) {
        List<UserResponse> users = userRepository.getAllUsers();
        List<OrderResponse> orders = orderRepository.getAllOrders();
        for (OrderResponse order : orders) {
            if(order.getUserId() == orderId){
                return orderRepository.deleteOrder(orderId);
            }
        }
        return null;
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.getAllOrders();
    }

    public OrderResponse getOrderById(int id) {
        List<OrderResponse> orders = orderRepository.getAllOrders();
        if (orders == null) {
            return null;
        }
       return orderRepository.getOrder(id);

    }

//---------------------------------------------------------------------------------------------

}
