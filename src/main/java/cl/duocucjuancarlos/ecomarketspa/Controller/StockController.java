package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.StockRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.StockResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.StockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody StockRequest request) {
        if (request == null || request.getProductoId() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El producto es obligatorio"));
        }
        StockResponse newStock = stockService.createStock(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newStock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de stock inválido"));
        }
        StockResponse stock = stockService.getStockById(id);
        if (stock == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Stock no encontrado"));
        }
        return ResponseEntity.ok(stock);
    }

    @GetMapping
    public ResponseEntity<List<StockResponse>> getAll() {
        return ResponseEntity.ok(stockService.getAllStocks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody StockRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de stock inválido"));
        }
        StockResponse updatedStock = stockService.updateStock(id, request);
        return ResponseEntity.ok(updatedStock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de stock inválido"));
        }
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}