package cl.duocucjuancarlos.ecomarketspa.Repository;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryRepository {
    private List<InventoryResponse> inventoryResponses;

    public InventoryRepository() {
        inventoryResponses = new ArrayList<>();
        inventoryResponses.add(new InventoryResponse(1, "shampoo", "bio", 10, 1000));
    }

    //contiene logica de negocio
    public InventoryResponse updateProduct(int elementNumber, InventoryRequest inventoryRequest) {
        if (elementNumber < 0 || elementNumber >= inventoryResponses.size()) {
            return null;
        }

        InventoryResponse product = inventoryResponses.get(elementNumber);

        boolean allNull = (inventoryRequest.getName() == null || inventoryRequest.getName().isEmpty())
                && (inventoryRequest.getDescription() == null || inventoryRequest.getDescription().isEmpty())
                && inventoryRequest.getPrice() <= 0
                && inventoryRequest.getQuantity() <= 0;

        if (allNull) {
            return product; // No cambia nada
        }

        if (inventoryRequest.getName() != null && !inventoryRequest.getName().isEmpty()) {
            product.setName(inventoryRequest.getName());
        }
        if (inventoryRequest.getDescription() != null && !inventoryRequest.getDescription().isEmpty()) {
            product.setDescription(inventoryRequest.getDescription());
        }
        if (inventoryRequest.getPrice() > 0) {
            product.setPrice(inventoryRequest.getPrice());
        }
        if (inventoryRequest.getQuantity() > 0) {
            product.setQuantity(inventoryRequest.getQuantity());
        }

        return product;
    }

    public InventoryResponse getInventoryById(int id) {
        if( id < 0 || id - 1 >= inventoryResponses.size()) {
            return null;
        }
        return inventoryResponses.get(id - 1);
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
    public InventoryResponse deleteProduct(int elementNumber) {return inventoryResponses.remove(elementNumber - 1);
    }

    public List<InventoryResponse> getInventoryResponses() {
        return inventoryResponses;
    }
}
