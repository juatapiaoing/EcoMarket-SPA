package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Inventory;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    private InventoryResponse toResponse(Inventory inventory) {
        return new InventoryResponse(
                inventory.getId(),
                inventory.getName(),
                inventory.getDescription(),
                inventory.getQuantity(),
                inventory.getPrice()
        );
    }

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public InventoryResponse getInventoryById(int id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        return inventory.map(this::toResponse).orElse(null);
    }

    public InventoryResponse addProduct(InventoryRequest inventoryRequest) {
        if (inventoryRequest == null) return null;
        Inventory inventory = new Inventory(null, inventoryRequest.getName(), inventoryRequest.getDescription(), inventoryRequest.getQuantity(), inventoryRequest.getPrice());
        inventory = inventoryRepository.save(inventory);
        return toResponse(inventory);
    }

    public InventoryResponse updateProduct(int id, InventoryRequest inventoryRequest) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isEmpty() || inventoryRequest == null) return null;
        Inventory inventory = optionalInventory.get();
        if (inventoryRequest.getName() != null) inventory.setName(inventoryRequest.getName());
        if (inventoryRequest.getDescription() != null) inventory.setDescription(inventoryRequest.getDescription());
        if (inventoryRequest.getQuantity() > 0) inventory.setQuantity(inventoryRequest.getQuantity());
        if (inventoryRequest.getPrice() > 0) inventory.setPrice(inventoryRequest.getPrice());
        inventory = inventoryRepository.save(inventory);
        return toResponse(inventory);
    }

    public InventoryResponse deleteProduct(int id) {
        Optional<Inventory> optionalInventory = inventoryRepository.findById(id);
        if (optionalInventory.isEmpty()) return null;
        Inventory inventory = optionalInventory.get();
        inventoryRepository.delete(inventory);
        return toResponse(inventory);
    }
}
