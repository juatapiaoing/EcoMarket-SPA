package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.OrderDetail;
import cl.duocucjuancarlos.ecomarketspa.Model.Product;
import cl.duocucjuancarlos.ecomarketspa.Model.Stock;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderDetailRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceImplTest {

    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    private Order order;
    private Stock stock;
    private OrderDetail orderDetail;
    private OrderDetailRequest orderDetailRequest;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1);

        Product product = new Product();
        product.setId(1);
        product.setNombre("Manzana");

        stock = new Stock();
        stock.setId(1);
        stock.setProducto(product);

        orderDetail = new OrderDetail();
        orderDetail.setId(1);
        orderDetail.setOrden(order);
        orderDetail.setStock(stock);
        orderDetail.setCantidad(5);
        orderDetail.setPrecioUnitario(500);

        orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrdenId(1);
        orderDetailRequest.setStockId(1);
        orderDetailRequest.setCantidad(5);
        orderDetailRequest.setPrecioUnitario(500);
    }

    @Test
    void createOrderDetail_success() {
        given(orderRepository.findById(1)).willReturn(Optional.of(order));
        given(stockRepository.findById(1)).willReturn(Optional.of(stock));
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(orderDetail);

        OrderDetailResponse response = orderDetailService.createOrderDetail(orderDetailRequest);

        assertNotNull(response);
        assertEquals(5, response.getCantidad());
        assertEquals("Manzana", response.getProductoNombre());
    }

    @Test
    void createOrderDetail_orderNotFound() {
        given(orderRepository.findById(99)).willReturn(Optional.empty());
        orderDetailRequest.setOrdenId(99);
        assertThrows(RuntimeException.class, () -> orderDetailService.createOrderDetail(orderDetailRequest));
    }

    @Test
    void getOrderDetailById_success() {
        given(orderDetailRepository.findById(1)).willReturn(Optional.of(orderDetail));
        OrderDetailResponse response = orderDetailService.getOrderDetailById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void getOrderDetailById_notFound() {
        given(orderDetailRepository.findById(99)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderDetailService.getOrderDetailById(99));
    }

    @Test
    void updateOrderDetail_success() {
        given(orderDetailRepository.findById(1)).willReturn(Optional.of(orderDetail));
        given(orderRepository.findById(1)).willReturn(Optional.of(order));
        given(stockRepository.findById(1)).willReturn(Optional.of(stock));
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(orderDetail);

        orderDetailRequest.setCantidad(10); // Actualizamos la cantidad
        OrderDetailResponse response = orderDetailService.updateOrderDetail(1, orderDetailRequest);

        assertNotNull(response);
        assertEquals(10, response.getCantidad());
    }

    @Test
    void updateOrderDetail_notFound() {
        given(orderDetailRepository.findById(99)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> orderDetailService.updateOrderDetail(99, orderDetailRequest));
    }

    @Test
    void deleteOrderDetail_success() {
        given(orderDetailRepository.existsById(1)).willReturn(true);
        assertDoesNotThrow(() -> orderDetailService.deleteOrderDetail(1));
        verify(orderDetailRepository).deleteById(1);
    }

    @Test
    void deleteOrderDetail_notFound() {
        given(orderDetailRepository.existsById(99)).willReturn(false);
        assertThrows(RuntimeException.class, () -> orderDetailService.deleteOrderDetail(99));
    }
}