package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.StockRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.StockResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Product;
import cl.duocucjuancarlos.ecomarketspa.Model.Stock;
import cl.duocucjuancarlos.ecomarketspa.Repository.ProductRepository;
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

@ExtendWith(MockitoExtension.class)
class StockServiceImplTest {
    @Mock
    private StockRepository stockRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private StockServiceImpl stockService;

    private Stock stock;
    private Product product;
    private StockRequest stockRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setNombre("Manzana");

        stock = new Stock();
        stock.setId(1);
        stock.setProducto(product);
        stock.setCantidadTotal(100);

        stockRequest = new StockRequest();
        stockRequest.setProductoId(1);
        stockRequest.setCantidadTotal(100);
    }

    @Test
    void createStock() {
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        given(stockRepository.save(any(Stock.class))).willReturn(stock);
        StockResponse response = stockService.createStock(stockRequest);
        assertNotNull(response);
        assertEquals(100, response.getCantidadTotal());
    }

    @Test
    void createStock_productNotFound() {
        given(productRepository.findById(99)).willReturn(Optional.empty());
        stockRequest.setProductoId(99);
        assertThrows(RuntimeException.class, () -> stockService.createStock(stockRequest));
    }
}