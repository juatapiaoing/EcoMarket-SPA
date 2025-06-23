package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;

import java.util.List;

public interface OrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailRequest request);
    OrderDetailResponse getOrderDetailById(Integer id);
    List<OrderDetailResponse> getAllOrderDetails();
    OrderDetailResponse updateOrderDetail(Integer id, OrderDetailRequest request);
    void deleteOrderDetail(Integer id);
}