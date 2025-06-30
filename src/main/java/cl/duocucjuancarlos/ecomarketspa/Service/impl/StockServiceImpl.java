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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StockServiceImpl implements StockService {

    private final StockRepository stockRepository;
    private final ProductRepository productRepository;
    private final MovimientoRepository movimientoRepository;

    public StockServiceImpl(StockRepository stockRepository, ProductRepository productRepository, MovimientoRepository movimientoRepository) {
        this.stockRepository = stockRepository;
        this.productRepository = productRepository;
        this.movimientoRepository = movimientoRepository;
    }

    private StockResponse toResponse(Stock stock) {
        StockResponse res = new StockResponse();
        res.setId(stock.getId());
        res.setProductoId(stock.getProducto().getId());
        res.setCantidadTotal(stock.getCantidadTotal());
        res.setMovimientoId(stock.getMovimiento() != null ? stock.getMovimiento().getId() : null);
        return res;
    }

    @Override
    @Transactional
    public StockResponse createStock(StockRequest request) {
        Product producto = productRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + request.getProductoId()));

        Stock stock = new Stock();
        stock.setProducto(producto);
        stock.setCantidadTotal(request.getCantidadTotal());

        if (request.getMovimientoId() != null) {
            Movimiento mov = movimientoRepository.findById(request.getMovimientoId())
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + request.getMovimientoId()));
            stock.setMovimiento(mov);
        }

        Stock savedStock = stockRepository.save(stock);
        return toResponse(savedStock);
    }

    @Override
    @Transactional(readOnly = true)
    public StockResponse getStockById(Integer id) {
        return stockRepository.findById(id).map(this::toResponse).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockResponse> getAllStocks() {
        return stockRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public StockResponse updateStock(Integer id, StockRequest request) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock no encontrado con ID: " + id));

        Product producto = productRepository.findById(request.getProductoId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + request.getProductoId()));

        stock.setProducto(producto);
        stock.setCantidadTotal(request.getCantidadTotal());

        if (request.getMovimientoId() != null) {
            Movimiento mov = movimientoRepository.findById(request.getMovimientoId())
                    .orElseThrow(() -> new RuntimeException("Movimiento no encontrado con ID: " + request.getMovimientoId()));
            stock.setMovimiento(mov);
        } else {
            stock.setMovimiento(null);
        }

        Stock updatedStock = stockRepository.save(stock);
        return toResponse(updatedStock);
    }

    @Override
    @Transactional
    public void deleteStock(Integer id) {
        if (!stockRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Stock no encontrado con ID: " + id);
        }
        stockRepository.deleteById(id);
    }
}