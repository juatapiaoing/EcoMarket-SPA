package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.StockRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.StockResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {
    @Mock
    private StockService stockService;
    @InjectMocks
    private StockController stockController;

    private StockRequest stockRequest;
    private StockResponse stockResponse;

    @BeforeEach
    void setUp() {
        stockRequest = new StockRequest();
        stockRequest.setProductoId(1);
        stockRequest.setCantidadTotal(100);

        stockResponse = new StockResponse();
        stockResponse.setId(1);
        stockResponse.setProductoId(1);
        stockResponse.setCantidadTotal(100);
    }

    @Test
    void create_success() {
        given(stockService.createStock(any(StockRequest.class))).willReturn(stockResponse);
        ResponseEntity<?> response = stockController.create(stockRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void create_badRequest_whenProductIdIsNull() {
        stockRequest.setProductoId(null);
        ResponseEntity<?> response = stockController.create(stockRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_success() {
        given(stockService.getStockById(1)).willReturn(stockResponse);
        ResponseEntity<?> response = stockController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getById_notFound() {
        given(stockService.getStockById(99)).willReturn(null);
        ResponseEntity<?> response = stockController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = stockController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(stockService.getAllStocks()).willReturn(Collections.singletonList(stockResponse));
        ResponseEntity<List<StockResponse>> response = stockController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void update_success() {
        given(stockService.updateStock(eq(1), any(StockRequest.class))).willReturn(stockResponse);
        ResponseEntity<?> response = stockController.update(1, stockRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = stockController.update(0, stockRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_throwsException_whenNotFound() {
        given(stockService.updateStock(eq(99), any(StockRequest.class))).willThrow(new RuntimeException("No encontrado"));
        assertThrows(RuntimeException.class, () -> stockController.update(99, stockRequest));
    }

    @Test
    void delete_success() {
        doNothing().when(stockService).deleteStock(1);
        ResponseEntity<?> response = stockController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(stockService).deleteStock(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = stockController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(stockService).deleteStock(99);
        assertThrows(RuntimeException.class, () -> stockController.delete(99));
    }
}