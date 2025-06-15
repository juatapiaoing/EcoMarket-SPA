package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.OrderRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderResponse testOrder;

    @BeforeEach
    void setUp() {
        testOrder = new OrderResponse(1, Arrays.asList(10, 20));
    }

    @Test
    void getAllOrders_returnsList() throws Exception {
        when(orderService.getAllOrders()).thenReturn(List.of(testOrder));
        mockMvc.perform(get("/api/v1/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1))
                .andExpect(jsonPath("$[0].productId[0]").value(10));
    }

    @Test
    void getOrderById_found() throws Exception {
        when(orderService.getOrderById(1)).thenReturn(testOrder);
        mockMvc.perform(get("/api/v1/orders/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void getOrderById_notFound() throws Exception {
        when(orderService.getOrderById(99)).thenReturn(null);
        mockMvc.perform(get("/api/v1/orders/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addOrder_success() throws Exception {
        OrderRequest req = new OrderRequest(Arrays.asList(10, 20));
        when(orderService.addOrder(eq(1), any(OrderRequest.class))).thenReturn(testOrder);

        mockMvc.perform(post("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userId").value(1));
    }

    @Test
    void addOrder_badRequest() throws Exception {
        when(orderService.addOrder(eq(1), any(OrderRequest.class))).thenReturn(null);
        mockMvc.perform(post("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OrderRequest())))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOrder_success() throws Exception {
        OrderRequest req = new OrderRequest(Arrays.asList(30, 40));
        OrderResponse updated = new OrderResponse(1, Arrays.asList(30, 40));
        when(orderService.updateOrder(eq(1), any(OrderRequest.class))).thenReturn(updated);

        mockMvc.perform(put("/api/v1/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId[0]").value(30));
    }

    @Test
    void updateOrder_notFound() throws Exception {
        when(orderService.updateOrder(eq(99), any(OrderRequest.class))).thenReturn(null);
        mockMvc.perform(put("/api/v1/orders/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new OrderRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOrder_success() throws Exception {
        when(orderService.deleteOrder(1)).thenReturn(testOrder);
        mockMvc.perform(delete("/api/v1/orders/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteOrder_notFound() throws Exception {
        when(orderService.deleteOrder(99)).thenReturn(null);
        mockMvc.perform(delete("/api/v1/orders/99"))
                .andExpect(status().isNotFound());
    }
}