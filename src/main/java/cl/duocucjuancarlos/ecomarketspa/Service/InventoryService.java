package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryRepository;

    public List<InventoryResponse> getInventory() {
        return inventoryRepository.getInventoryResponses();
    }

    public InventoryResponse getInventoryById(int id) {
        List<InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        if (id <= 0 || id - 1 >= inventoryResponses.size()) {
            return null; // or throw an exception
        }
        return inventoryRepository.getInventoryById(id);
    }

    public InventoryResponse addInventory(InventoryRequest inventoryRequest) {
        // Validar que el objeto no sea nulo

        if (inventoryRequest.getName() != null && !inventoryRequest.getName().isEmpty() && inventoryRequest.getDescription() != null && !inventoryRequest.getDescription().isEmpty() && inventoryRequest.getPrice() > 0 && inventoryRequest.getQuantity() > 0 ) {
            return  inventoryRepository.addProduct(inventoryRequest);
        }
        return null;
    }

    public InventoryResponse updateInventory(int elementNumber, InventoryRequest inventoryRequest) {
        List<InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        if (elementNumber >= 0 && elementNumber < inventoryResponses.size()) {
            return  inventoryRepository.updateProduct(elementNumber, inventoryRequest);
        }
        return null;
    }

    public InventoryResponse deleteInventory(int elementNumber) {
        List <InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        if (elementNumber >= 0 && elementNumber - 1 <= inventoryResponses.size()) {
            return inventoryRepository.deleteProduct(elementNumber);
        }
        return null;
    }
}
