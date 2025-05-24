package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    private List<OrderResponse> orders = new ArrayList<>();
    private OrderResponse order;

    public List<OrderResponse> getAllOrders() { return orders;}

    public OrderResponse getOrder(int userId) {
        return orders.get(userId);
    }


    public OrderResponse addOrder(int userId, OrderRequest orderRequest) {
        OrderResponse order = new OrderResponse();
        order.setUserId(userId);
        order.setProductId(new ArrayList<>(orderRequest.getProductId()));
        orders.add(order);
        return order;
    }



    public OrderResponse updateOrder(int userId, OrderRequest orderRequest) {
           orders.get(userId).setProductId(new ArrayList<>(orderRequest.getProductId()));
           return orders.get(userId);
    }



    public OrderResponse deleteOrder(int userId) {
        return orders.remove(userId);
    }
}
