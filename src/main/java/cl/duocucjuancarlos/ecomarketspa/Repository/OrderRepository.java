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
        for (OrderResponse order : orders) {
            if (order.getUserId() == userId) {
                this.order = order;
                break;
            }
        }
        return this.order;
    }


    public OrderResponse addOrder(int userId, OrderRequest orderRequest) {
        OrderResponse order = new OrderResponse();
        order.setUserId(userId);
        order.setProductId(new ArrayList<>(orderRequest.getProductId()));
        orders.add(order);
        return order;
    }



    public OrderResponse updateOrder(int userId, OrderRequest orderRequest) {
           for (OrderResponse order : orders) {
                if (order.getUserId() == userId) {
                    order.setProductId(new ArrayList<>(orderRequest.getProductId()));
                    return order;
                }
           }
           return null;
    }



    public OrderResponse deleteOrder(int userId) {
        for( OrderResponse order : orders) {
            if (order.getUserId() == userId) {
                orders.remove(order);
                return order;
            }
        }
        return null;
    }
}
