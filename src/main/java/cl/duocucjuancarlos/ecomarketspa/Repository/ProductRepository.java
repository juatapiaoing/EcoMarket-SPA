package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Response.ProductResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private List<ProductResponse> productsResponse;

    public ProductRepository() {
        productsResponse = new ArrayList<>();
        productsResponse.add(new ProductResponse(1000,"Shampoo","envase biodegradable"));
    }

    private List<ProductResponse> getProductsResponse() {
        return productsResponse;
    }

}
