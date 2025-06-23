package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.OrderDetail;
import cl.duocucjuancarlos.ecomarketspa.Model.Stock;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderDetailRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.StockRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StockRepository stockRepository;

    private OrderDetailResponse toResponse(OrderDetail detail) {
        OrderDetailResponse res = new OrderDetailResponse();
        res.setStockId(detail.getStock().getId());
        res.setProductoNombre(detail.getStock().getProducto().getNombre());
        res.setCantidad(detail.getCantidad());
        res.setPrecioUnitario(detail.getPrecioUnitario());
        return res;
    }

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailRequest request) {
        OrderDetail detail = new OrderDetail();
        Order order = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        detail.setOrden(order);
        detail.setStock(stock);
        detail.setCantidad(request.getCantidad());
        detail.setPrecioUnitario(request.getPrecioUnitario());
        return toResponse(orderDetailRepository.save(detail));
    }

    @Override
    public OrderDetailResponse getOrderDetailById(Integer id) {
        OrderDetail detail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        return toResponse(detail);
    }

    @Override
    public List<OrderDetailResponse> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Integer id, OrderDetailRequest request) {
        OrderDetail detail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle no encontrado"));
        Order order = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        detail.setOrden(order);
        detail.setStock(stock);
        detail.setCantidad(request.getCantidad());
        detail.setPrecioUnitario(request.getPrecioUnitario());
        return toResponse(orderDetailRepository.save(detail));
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        orderDetailRepository.deleteById(id);
    }
}