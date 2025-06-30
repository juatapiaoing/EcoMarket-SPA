package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.ProductRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.ProductResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.ProductService;
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
class ProductControllerTest {

    @Mock
    private ProductService productService;
    @InjectMocks
    private ProductController productController;

    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    void setUp() {
        productRequest = new ProductRequest();
        productRequest.setNombre("Manzana");
        productResponse = new ProductResponse();
        productResponse.setId(1);
        productResponse.setNombre("Manzana");
    }

    @Test
    void create_success() {
        given(productService.createProduct(any(ProductRequest.class))).willReturn(productResponse);
        ResponseEntity<?> response = productController.create(productRequest);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
    }

    @Test
    void create_badRequest_whenNameIsNull() {
        productRequest.setNombre(null);
        ResponseEntity<?> response = productController.create(productRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getById_success() {
        given(productService.getProductById(1)).willReturn(productResponse);
        ResponseEntity<?> response = productController.getById(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productResponse, response.getBody());
    }

    @Test
    void getById_notFound() {
        given(productService.getProductById(99)).willReturn(null);
        ResponseEntity<?> response = productController.getById(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getById_invalidId() {
        ResponseEntity<?> response = productController.getById(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getAll_success() {
        given(productService.getAllProducts()).willReturn(Collections.singletonList(productResponse));
        ResponseEntity<List<ProductResponse>> response = productController.getAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void update_success() {
        given(productService.updateProduct(eq(1), any(ProductRequest.class))).willReturn(productResponse);
        ResponseEntity<?> response = productController.update(1, productRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void update_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = productController.update(0, productRequest);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void update_throwsException_whenNotFound() {
        given(productService.updateProduct(eq(99), any(ProductRequest.class))).willThrow(new RuntimeException("No encontrado"));
        assertThrows(RuntimeException.class, () -> productController.update(99, productRequest));
    }

    @Test
    void delete_success() {
        doNothing().when(productService).deleteProduct(1);
        ResponseEntity<?> response = productController.delete(1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteProduct(1);
    }

    @Test
    void delete_badRequest_whenIdIsInvalid() {
        ResponseEntity<?> response = productController.delete(0);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void delete_throwsException_whenNotFound() {
        doThrow(new RuntimeException("No encontrado")).when(productService).deleteProduct(99);
        assertThrows(RuntimeException.class, () -> productController.delete(99));
    }
}