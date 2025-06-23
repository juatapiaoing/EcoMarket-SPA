package cl.duocucjuancarlos.ecomarketspa.service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.ProductRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.ProductResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Product;
import cl.duocucjuancarlos.ecomarketspa.Repository.ProductRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private ProductResponse toResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setNombre(product.getNombre());
        response.setDescripcion(product.getDescripcion());
        response.setTipoProducto(product.getTipoProducto());
        return response;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product();
        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setTipoProducto(request.getTipoProducto());
        return toResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return toResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponse updateProduct(Integer id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        product.setNombre(request.getNombre());
        product.setDescripcion(request.getDescripcion());
        product.setTipoProducto(request.getTipoProducto());
        return toResponse(productRepository.save(product));
    }

    @Override
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
}