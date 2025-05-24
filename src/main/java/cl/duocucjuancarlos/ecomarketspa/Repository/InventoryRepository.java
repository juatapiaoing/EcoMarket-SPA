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

    //contiene logica de negocio
    public InventoryResponse updateProduct(int elementNumber, InventoryRequest inventoryRequest) {
        inventoryResponses.get(elementNumber).setId(elementNumber);
        inventoryResponses.get(elementNumber).setName(inventoryRequest.getName());
        inventoryResponses.get(elementNumber).setDescription(inventoryRequest.getDescription());
        inventoryResponses.get(elementNumber).setPrice(inventoryRequest.getPrice());
        inventoryResponses.get(elementNumber).setQuantity(inventoryRequest.getQuantity());
        return inventoryResponses.get(elementNumber);
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

        return inventoryResponses.get(id-1);
    }

    //contiene logica de negocio
    public InventoryResponse deleteProduct(int elementNumber) {
       return inventoryResponses.remove(elementNumber);
    }
}
