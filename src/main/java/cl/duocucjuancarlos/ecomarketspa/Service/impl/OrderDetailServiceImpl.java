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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, OrderRepository orderRepository, StockRepository stockRepository) {
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
        this.stockRepository = stockRepository;
    }

    private OrderDetailResponse toResponse(OrderDetail detail) {
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
    public OrderDetailResponse createOrderDetail(OrderDetailRequest request) {
        Order order = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + request.getOrdenId()));
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado con ID: " + request.getStockId()));

        OrderDetail detail = new OrderDetail();
        detail.setOrden(order);
        detail.setStock(stock);
        detail.setCantidad(request.getCantidad());
        detail.setPrecioUnitario(request.getPrecioUnitario());

        OrderDetail savedDetail = orderDetailRepository.save(detail);
        return toResponse(savedDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetailById(Integer id) {
        return orderDetailRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDetailResponse> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderDetailResponse updateOrderDetail(Integer id, OrderDetailRequest request) {
        OrderDetail detail = orderDetailRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de orden no encontrado con ID: " + id));

        Order order = orderRepository.findById(request.getOrdenId())
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + request.getOrdenId()));
        Stock stock = stockRepository.findById(request.getStockId())
                .orElseThrow(() -> new RuntimeException("Stock no encontrado con ID: " + request.getStockId()));

        detail.setOrden(order);
        detail.setStock(stock);
        detail.setCantidad(request.getCantidad());
        detail.setPrecioUnitario(request.getPrecioUnitario());

        OrderDetail updatedDetail = orderDetailRepository.save(detail);
        return toResponse(updatedDetail);
    }

    @Override
    @Transactional
    public void deleteOrderDetail(Integer id) {
        if (!orderDetailRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Detalle de orden no encontrado con ID: " + id);
        }
        orderDetailRepository.deleteById(id);
    }
}