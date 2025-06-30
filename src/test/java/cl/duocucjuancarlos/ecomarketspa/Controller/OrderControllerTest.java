package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderService orderService;
    @InjectMocks
    private OrderController orderController;

    private OrderRequest orderRequest;
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setUsuarioId(1);

        orderResponse = new OrderResponse();
        orderResponse.setId(1);
        orderResponse.setUsuarioId(1);
    }

    @Test
    void create_shouldReturn201() {
        given(orderService.createOrder(any(OrderRequest.class))).willReturn(orderResponse);
        ResponseEntity<?> response = orderController.create(orderRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void create_whenUserIdIsNull_shouldReturn400() {
        orderRequest.setUsuarioId(null);
        ResponseEntity<?> response = orderController.create(orderRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}