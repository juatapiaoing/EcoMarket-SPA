package cl.duocucjuancarlos.ecomarketspa.Service;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(OrderRequest request);
    OrderResponse getOrderById(Integer id);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrder(Integer id, OrderRequest request);
    void deleteOrder(Integer id);
}