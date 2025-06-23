package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.StockRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.StockResponse;

import java.util.List;

public interface StockService {
    StockResponse createStock(StockRequest request);
    StockResponse getStockById(Integer id);
    List<StockResponse> getAllStocks();
    StockResponse updateStock(Integer id, StockRequest request);
    void deleteStock(Integer id);
}