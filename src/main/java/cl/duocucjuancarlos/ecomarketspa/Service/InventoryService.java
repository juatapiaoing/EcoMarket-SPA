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

    public InventoryRequest addInventory(InventoryRequest inventoryRequest) {
        List<InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        InventoryResponse newInventory = new InventoryResponse(inventoryRequest.getId(),
                   inventoryRequest.getName(),
                   inventoryRequest.getDescription(),
                   inventoryRequest.getQuantity(),
                   inventoryRequest.getPrice());
        inventoryResponses.add(newInventory);
        return inventoryRequest;
    }

    public InventoryRequest updateInventory(InventoryRequest inventoryRequest) {
        List<InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        int id = inventoryRequest.getId();
        String name = inventoryRequest.getName();
        String description = inventoryRequest.getDescription();
        int quantity = inventoryRequest.getQuantity();
        int price = inventoryRequest.getPrice();
        InventoryResponse inventoryResponse = inventoryRepository.getInventoryById(id);
        inventoryResponse.setId(id);
        inventoryResponse.setName(name);
        inventoryResponse.setDescription(description);
        inventoryResponse.setQuantity(quantity);
        inventoryResponse.setPrice(price);
        return inventoryRequest;
    }

    public InventoryResponse deleteInventory(int elementNumber) {
        List<InventoryResponse> inventoryResponses = inventoryRepository.getInventoryResponses();
        int id = inventoryResponses.get(elementNumber).getId();
        inventoryResponses.remove(id);
        return inventoryRepository.getInventoryById(id);
    }
}
