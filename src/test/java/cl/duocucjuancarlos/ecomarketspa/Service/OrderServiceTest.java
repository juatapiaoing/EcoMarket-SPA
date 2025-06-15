package cl.duocucjuancarlos.ecomarketspa.Service;

import org.junit.jupiter.api.Test;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private User testUser;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User(1, "1-9", "Juan", "Tapia", "mail@mail.com", "1234");
        testOrder = new Order(101, testUser, Arrays.asList(10, 20));
    }

    @Test
    void getAllOrders_returnsList() {
        when(orderRepository.findAll()).thenReturn(List.of(testOrder));
        List<OrderResponse> result = orderService.getAllOrders();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getUserId());
        assertEquals(Arrays.asList(10, 20), result.get(0).getProductId());
    }

    @Test
    void getAllOrders_emptyList() {
        when(orderRepository.findAll()).thenReturn(Collections.emptyList());
        List<OrderResponse> result = orderService.getAllOrders();
        assertTrue(result.isEmpty());
    }

    @Test
    void getOrderById_found() {
        when(orderRepository.findById(101)).thenReturn(Optional.of(testOrder));
        OrderResponse result = orderService.getOrderById(101);
        assertNotNull(result);
        assertEquals(1, result.getUserId());
    }

    @Test
    void getOrderById_notFound() {
        when(orderRepository.findById(999)).thenReturn(Optional.empty());
        OrderResponse result = orderService.getOrderById(999);
        assertNull(result);
    }

    @Test
    void addOrder_success() {
        OrderRequest req = new OrderRequest(Arrays.asList(10, 20));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(123);
            return o;
        });

        OrderResponse result = orderService.addOrder(1, req);

        assertNotNull(result);
        assertEquals(1, result.getUserId());
        assertEquals(Arrays.asList(10, 20), result.getProductId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void addOrder_userNotFound() {
        OrderRequest req = new OrderRequest(Arrays.asList(10, 20));
        when(userRepository.findById(2)).thenReturn(Optional.empty());
        OrderResponse result = orderService.addOrder(2, req);
        assertNull(result);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void updateOrder_success() {
        OrderRequest req = new OrderRequest(Arrays.asList(30, 40));
        when(orderRepository.findById(101)).thenReturn(Optional.of(testOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderResponse result = orderService.updateOrder(101, req);

        assertNotNull(result);
        assertEquals(Arrays.asList(30, 40), result.getProductId());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void updateOrder_orderNotFound() {
        OrderRequest req = new OrderRequest(Arrays.asList(30, 40));
        when(orderRepository.findById(999)).thenReturn(Optional.empty());
        OrderResponse result = orderService.updateOrder(999, req);
        assertNull(result);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void updateOrder_nullRequest() {
        when(orderRepository.findById(101)).thenReturn(Optional.of(testOrder));
        OrderResponse result = orderService.updateOrder(101, null);
        assertNull(result);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void deleteOrder_success() {
        when(orderRepository.findById(101)).thenReturn(Optional.of(testOrder));
        OrderResponse result = orderService.deleteOrder(101);
        assertNotNull(result);
        verify(orderRepository).delete(testOrder);
    }

    @Test
    void deleteOrder_orderNotFound() {
        when(orderRepository.findById(888)).thenReturn(Optional.empty());
        OrderResponse result = orderService.deleteOrder(888);
        assertNull(result);
        verify(orderRepository, never()).delete(any());
    }
}