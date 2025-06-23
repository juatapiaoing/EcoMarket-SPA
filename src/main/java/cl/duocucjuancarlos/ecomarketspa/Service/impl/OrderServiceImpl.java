package cl.duocucjuancarlos.ecomarketspa.Service.impl;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.OrderDetail;
import cl.duocucjuancarlos.ecomarketspa.Model.Stock;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderDetailRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.StockRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StockRepository stockRepository;

    private OrderResponse toResponse(Order order) {
        OrderResponse res = new OrderResponse();
        res.setId(order.getId());
        res.setUsuarioId(order.getUsuario().getId());
        res.setUsuarioNombre(order.getUsuario().getNombre());
        res.setFechaCreacion(order.getFechaCreacion());
        res.setEstado(order.getEstado());
        res.setDetalles(order.getDetalles() != null ? order.getDetalles()
                .stream()
                .map(this::toDetailResponse)
                .collect(Collectors.toList())
                : null);
        return res;
    }

    private OrderDetailResponse toDetailResponse(OrderDetail detail) {
        OrderDetailResponse res = new OrderDetailResponse();
        res.setStockId(detail.getStock().getId());
        res.setProductoNombre(detail.getStock().getProducto().getNombre());
        res.setCantidad(detail.getCantidad());
        res.setPrecioUnitario(detail.getPrecioUnitario());
        return res;
    }

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Order order = new Order();
        order.setUsuario(usuario);
        order.setFechaCreacion(new Date());
        order.setEstado(request.getEstado());

        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> detalles = request.getDetalles().stream().map(d -> {
            OrderDetail od = new OrderDetail();
            od.setOrden(savedOrder);
            Stock stock = stockRepository.findById(d.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
            od.setStock(stock);
            od.setCantidad(d.getCantidad());
            od.setPrecioUnitario(d.getPrecioUnitario());
            return orderDetailRepository.save(od);
        }).collect(Collectors.toList());

        savedOrder.setDetalles(detalles);
        return toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Integer id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        return toResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        order.setUsuario(usuario);
        order.setEstado(request.getEstado());
        // No actualizamos detalles aquí por simplicidad
        return toResponse(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Integer id) {
        orderRepository.deleteById(id);
    }
}