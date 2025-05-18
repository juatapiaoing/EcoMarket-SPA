package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


}
