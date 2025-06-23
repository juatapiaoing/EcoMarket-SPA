package cl.duocucjuancarlos.ecomarketspa.Service.impl;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.StockRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.StockResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Movimiento;
import cl.duocucjuancarlos.ecomarketspa.Model.Product;
import cl.duocucjuancarlos.ecomarketspa.Model.Stock;
import cl.duocucjuancarlos.ecomarketspa.Repository.MovimientoRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.ProductRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.StockRepository;
import cl.duocucjuancarlos.ecomarketspa.Service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MovimientoRepository movimientoRepository;

    private StockResponse toResponse(Stock stock) {
        StockResponse res = new StockResponse();
        res.setId(stock.getId());
        res.setProductoId(stock.getProducto().getId());
        res.setCantidadTotal(stock.getCantidadTotal());
        res.setMovimientoId(stock.getMovimiento() != null ? stock.getMovimiento().getId() : null);
        return res;
    }

    @Override
    public StockResponse createStock(StockRequest request) {
        Stock stock = new Stock();
        Product producto = productRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        stock.setProducto(producto);
        stock.setCantidadTotal(request.getCantidadTotal());
        if(request.getMovimientoId() != null) {
            Movimiento mov = movimientoRepository.findById(request.getMovimientoId())
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
            stock.setMovimiento(mov);
        }
        return toResponse(stockRepository.save(stock));
    }

    @Override
    public StockResponse getStockById(Integer id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        return toResponse(stock);
    }

    @Override
    public List<StockResponse> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockResponse updateStock(Integer id, StockRequest request) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado"));
        Product producto = productRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        stock.setProducto(producto);
        stock.setCantidadTotal(request.getCantidadTotal());
        if(request.getMovimientoId() != null) {
            Movimiento mov = movimientoRepository.findById(request.getMovimientoId())
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
            stock.setMovimiento(mov);
        }
        return toResponse(stockRepository.save(stock));
    }

    @Override
    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }
}