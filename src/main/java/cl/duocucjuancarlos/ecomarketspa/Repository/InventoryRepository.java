package cl.duocucjuancarlos.ecomarketspa.Repository;

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
        inventoryResponses.add(new InventoryResponse(100, "shampoo", "bio", 10, 1000));
    }


    public List<InventoryResponse> getInventoryResponses() {
        return inventoryResponses;
    }


    public void addInventory() {

    }

    public InventoryResponse getInventoryById(int id) {
        return inventoryResponses.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }
}
