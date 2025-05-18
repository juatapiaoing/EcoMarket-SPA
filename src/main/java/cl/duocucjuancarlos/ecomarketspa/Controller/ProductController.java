package cl.duocucjuancarlos.ecomarketspa.Controller;


import cl.duocucjuancarlos.ecomarketspa.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;



}
