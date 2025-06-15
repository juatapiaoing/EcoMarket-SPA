package cl.duocucjuancarlos.ecomarketspa.Controller;


import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable int invoiceId) {
        InvoiceResponse invoice = invoiceService.getInvoiceById(invoiceId);
        if (invoice == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(invoice);
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> addInvoice(@RequestBody InvoiceRequest invoiceRequest) {
        InvoiceResponse invoice = invoiceService.addInvoice(invoiceRequest);
        if (invoice == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(201).body(invoice);
    }

    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable int invoiceId) {
        InvoiceResponse invoice = invoiceService.deleteInvoice(invoiceId);
        if (invoice == null) return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }

}
