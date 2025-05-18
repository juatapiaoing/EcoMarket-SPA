package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InventoryService;
import cl.duocucjuancarlos.ecomarketspa.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @GetMapping()
    public ResponseEntity<List<InventoryResponse>> getInventory() {
        return ResponseEntity.ok(inventoryService.getInventory());
    }

    @GetMapping("/{elementNumber}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable int elementNumber) {
        return ResponseEntity.ok(inventoryService.getInventoryById(elementNumber));
    }

    @PostMapping("/add")
     public InventoryRequest addInventory(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.addInventory(inventoryRequest);
    }

    @PutMapping("/{elementNumber}")
    public InventoryRequest updateInventory(@PathVariable int elementNumber, @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.updateInventory(inventoryRequest);
    }

    @DeleteMapping("/{elementNumber}")
    public InventoryResponse deleteInventory(@PathVariable int elementNumber) {
        return inventoryService.deleteInventory(elementNumber);
    }
}
