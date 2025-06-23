package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.ProductRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Integer id);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Integer id, ProductRequest request);
    void deleteProduct(Integer id);
}