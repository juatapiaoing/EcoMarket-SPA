package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Service.impl.ProductServiceImpl;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.ProductRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.ProductResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Product;
import cl.duocucjuancarlos.ecomarketspa.Repository.ProductRepository;
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
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1);
        product.setNombre("Manzana");
        product.setDescripcion("Roja y jugosa");
        product.setTipoProducto("Fruta");

        productRequest = new ProductRequest();
        productRequest.setNombre("Manzana");
        productRequest.setDescripcion("Roja y jugosa");
        productRequest.setTipoProducto("Fruta");
    }

    @Test
    void createProduct() {
        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductResponse response = productService.createProduct(productRequest);

        assertNotNull(response);
        assertEquals("Manzana", response.getNombre());
    }

    @Test
    void getProductById_success() {
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        ProductResponse response = productService.getProductById(1);
        assertNotNull(response);
        assertEquals(1, response.getId());
    }

    @Test
    void getProductById_notFound() {
        given(productRepository.findById(99)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.getProductById(1));
    }

    @Test
    void updateProduct_success() {
        given(productRepository.findById(1)).willReturn(Optional.of(product));
        given(productRepository.save(any(Product.class))).willReturn(product);

        productRequest.setNombre("Manzana Verde");
        ProductResponse response = productService.updateProduct(1, productRequest);

        assertNotNull(response);
        assertEquals("Manzana Verde", response.getNombre());
    }

    @Test
    void updateProduct_notFound() {
        given(productRepository.findById(99)).willReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> productService.updateProduct(99, productRequest));
    }

    @Test
    void deleteProduct_success() {
        given(productRepository.existsById(1)).willReturn(true);
        assertDoesNotThrow(() -> productService.deleteProduct(1));
        verify(productRepository).deleteById(1);
    }

    @Test
    void deleteProduct_notFound() {
        given(productRepository.existsById(99)).willReturn(false);
        assertThrows(RuntimeException.class, () -> productService.deleteProduct(99));
    }
}