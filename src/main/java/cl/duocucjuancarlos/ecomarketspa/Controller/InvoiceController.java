package cl.duocucjuancarlos.ecomarketspa.Controller;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody InvoiceRequest request) {
        if (request == null || request.getTotalPedido() == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El total del pedido es obligatorio"));
        }
        InvoiceResponse newInvoice = invoiceService.createInvoice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInvoice);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de factura inválido"));
        }
        InvoiceResponse invoice = invoiceService.getInvoiceById(id);
        if (invoice == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Factura no encontrada"));
        }
        return ResponseEntity.ok(invoice);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody InvoiceRequest request) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de factura inválido"));
        }
        InvoiceResponse updatedInvoice = invoiceService.updateInvoice(id, request);
        return ResponseEntity.ok(updatedInvoice);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().body(Map.of("error", "ID de factura inválido"));
        }
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}