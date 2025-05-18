package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class InventoryRepository {

    private List<InventoryResponse> inventoryResponses;

    public InventoryRepository() {
        inventoryResponses = new ArrayList<>();
        inventoryResponses.add(new InventoryResponse(1, "shampoo", "bio", 10, 1000));
    }

    public InventoryResponse updateProduct(int elementNumber, InventoryRequest inventoryRequest) {
        for (InventoryResponse inventoryResponse : inventoryResponses) {
            if(inventoryResponse.getId() == elementNumber) {
                inventoryResponse.setName(inventoryRequest.getName());
                inventoryResponse.setDescription(inventoryRequest.getDescription());
                inventoryResponse.setQuantity(inventoryRequest.getQuantity());
                inventoryResponse.setPrice(inventoryRequest.getPrice());
                return inventoryResponses.get(inventoryResponses.indexOf(inventoryResponse));
            }
        }
        return null;
    }

    public List<InventoryResponse> getInventoryResponses() {
        return inventoryResponses;
    }

    public InventoryResponse getInventoryById(int id) {
        return inventoryResponses.get(id);
    }


    public InventoryResponse addProduct(InventoryRequest inventoryRequest) {
        int id = inventoryResponses.size() + 1;
        String name = inventoryRequest.getName();
        String description = inventoryRequest.getDescription();
        int price = inventoryRequest.getPrice();
        int quantity = inventoryRequest.getQuantity();
        inventoryResponses.add(new InventoryResponse(id, name, description, price, quantity));

        return inventoryResponses.get(id);
    }

    public InventoryResponse deleteProduct(int elementNumber) {
        if (inventoryResponses.get(elementNumber).getId() == elementNumber + 1) {
            return inventoryResponses.remove(elementNumber);
        }
        return null;
    }
}
