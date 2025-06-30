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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UserRepository userRepository;
    private final StockRepository stockRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, UserRepository userRepository, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.userRepository = userRepository;
        this.stockRepository = stockRepository;
    }

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
        res.setId(detail.getOrden().getId());
        res.setStockId(detail.getStock().getId());
        res.setProductoNombre(detail.getStock().getProducto().getNombre());
        res.setCantidad(detail.getCantidad());
        res.setPrecioUnitario(detail.getPrecioUnitario());
        return res;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        User usuario = userRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        Order order = new Order();
        order.setUsuario(usuario);
        order.setFechaCreacion(new Date());
        order.setEstado("PENDIENTE");

        Order savedOrder = orderRepository.save(order);

        List<OrderDetail> detalles = request.getDetalles().stream().map(detalleReq -> {
            Stock stock = stockRepository.findById(detalleReq.getStockId())
                    .orElseThrow(() -> new RuntimeException("Stock no encontrado con ID: " + detalleReq.getStockId()));

            if (stock.getCantidadTotal() < detalleReq.getCantidad()) {
                throw new IllegalStateException("Stock insuficiente para el producto: " + stock.getProducto().getNombre());
            }
            stock.setCantidadTotal(stock.getCantidadTotal() - detalleReq.getCantidad());

            OrderDetail od = new OrderDetail();
            od.setOrden(savedOrder);
            od.setStock(stock);
            od.setCantidad(detalleReq.getCantidad());
            od.setPrecioUnitario(detalleReq.getPrecioUnitario());
            return orderDetailRepository.save(od);
        }).collect(Collectors.toList());

        savedOrder.setDetalles(detalles);
        return toResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Integer id) {
        return orderRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Integer id, OrderRequest request) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada para actualizar con ID: " + id));

        order.setEstado(request.getEstado());

        Order updatedOrder = orderRepository.save(order);
        return toResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Orden no encontrada con ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}