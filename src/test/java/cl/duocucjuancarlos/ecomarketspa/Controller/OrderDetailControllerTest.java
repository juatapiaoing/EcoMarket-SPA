package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderDetailRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderDetailResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderDetailService;
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
class OrderDetailControllerTest {

    @Mock
    private OrderDetailService orderDetailService;
    @InjectMocks
    private OrderDetailController orderDetailController;

    private OrderDetailRequest orderDetailRequest;
    private OrderDetailResponse orderDetailResponse;

    @BeforeEach
    void setUp() {
        orderDetailRequest = new OrderDetailRequest();
        orderDetailRequest.setOrdenId(1);

        orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setId(1);
    }

    @Test
    void create_shouldReturn201() {
        given(orderDetailService.createOrderDetail(any(OrderDetailRequest.class))).willReturn(orderDetailResponse);
        ResponseEntity<?> response = orderDetailController.create(orderDetailRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void create_whenOrderIdIsNull_shouldReturn400() {
        orderDetailRequest.setOrdenId(null);
        ResponseEntity<?> response = orderDetailController.create(orderDetailRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}