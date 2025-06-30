package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.*;
import cl.duocucjuancarlos.ecomarketspa.Repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StockRepository stockRepository;
    @InjectMocks
    private OrderServiceImpl orderService;

    private User user;
    private Stock stock;
    private Order order;
    private OrderRequest orderRequest;
    private OrderDetailRequest orderDetailRequest;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);

        Product product = new Product();
        product.setId(1);

        stock = new Stock();
        stock.setId(1);
        stock.setProducto(product);
        stock.setCantidadTotal(100);

        order = new Order();
        order.setId(1);
        order.setUsuario(user);

        orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setStockId(1);
        orderDetailRequest.setCantidad(10);
        orderDetailRequest.setPrecioUnitario(500);

        orderRequest = new OrderRequest();
        orderRequest.setUsuarioId(1);
        orderRequest.setEstado("PENDIENTE");
        orderRequest.setDetalles(Collections.singletonList(orderDetailRequest));
    }

    @Test
    void createOrder_success() {
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(stockRepository.findById(1)).willReturn(Optional.of(stock));
        given(orderRepository.save(any(Order.class))).willReturn(order);
        given(orderDetailRepository.save(any(OrderDetail.class))).willReturn(new OrderDetail());

        OrderResponse response = orderService.createOrder(orderRequest);

        assertNotNull(response);
        assertEquals("PENDIENTE", response.getEstado());
        assertEquals(90, stock.getCantidadTotal()); // Verifica que el stock se descuenta
    }

    @Test
    void createOrder_stockInsuficiente() {
        stock.setCantidadTotal(5); // Menos stock del que se pide
        given(userRepository.findById(1)).willReturn(Optional.of(user));
        given(stockRepository.findById(1)).willReturn(Optional.of(stock));
        given(orderRepository.save(any(Order.class))).willReturn(order);

        assertThrows(RuntimeException.class, () -> orderService.createOrder(orderRequest));
    }
}