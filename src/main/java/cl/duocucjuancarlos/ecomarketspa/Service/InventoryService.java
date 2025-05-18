package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public List<InventoryResponse> getInventory() {
        return inventoryRepository.getInventoryResponses();
    }

    public InventoryResponse getInventoryById(int id) {
        return inventoryRepository.getInventoryById(id);
    }

    public InventoryResponse addInventory(InventoryRequest inventoryRequest) {
        return  inventoryRepository.addProduct(inventoryRequest);
    }

    public InventoryResponse updateInventory(int elementNumber, InventoryRequest inventoryRequest) {
        return  inventoryRepository.updateProduct(elementNumber, inventoryRequest);
    }

    public InventoryResponse deleteInventory(int elementNumber) {
        return inventoryRepository.deleteProduct(elementNumber);
    }
}
