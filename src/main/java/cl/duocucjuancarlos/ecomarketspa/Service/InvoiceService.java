package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Model.Inventory;
import cl.duocucjuancarlos.ecomarketspa.Model.Invoice;
import cl.duocucjuancarlos.ecomarketspa.Model.Order;
import cl.duocucjuancarlos.ecomarketspa.Model.User;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    private InvoiceResponse toResponse(Invoice invoice) {
        return new InvoiceResponse(
                invoice.getId(),
                invoice.getRun(),
                invoice.getName(),
                invoice.getProducts(),
                invoice.getPrices(),
                invoice.getTotal()
        );
    }

    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(this::toResponse).collect(Collectors.toList());
    }

    public InvoiceResponse getInvoiceById(int id) {
        Optional<Invoice> invoice = invoiceRepository.findById(id);
        return invoice.map(this::toResponse).orElse(null);
    }

    public InvoiceResponse addInvoice(InvoiceRequest request) {
        if (request == null || request.getOrderId() == null) {
            return null;
        }
        Optional<Order> orderOpt = orderRepository.findById(request.getOrderId());
        if (orderOpt.isEmpty()) return null;
        Order order = orderOpt.get();

        User user = order.getUser();
        List<Integer> productIds = order.getProductId();
        List<Inventory> inventories = inventoryRepository.findAllById(productIds);

        List<String> productNames = inventories.stream().map(Inventory::getName).collect(Collectors.toList());
        List<Integer> prices = inventories.stream().map(Inventory::getPrice).collect(Collectors.toList());
        int total = prices.stream().mapToInt(Integer::intValue).sum();

        Invoice invoice = new Invoice(
                null,
                user.getRun(),
                user.getFirstName() + " " + user.getLastName(),
                productNames,
                prices,
                total
        );
        Invoice saved = invoiceRepository.save(invoice);
        return toResponse(saved);
    }

    public InvoiceResponse deleteInvoice(int id) {
        Optional<Invoice> optionalInvoice = invoiceRepository.findById(id);
        if (optionalInvoice.isEmpty()) return null;
        Invoice invoice = optionalInvoice.get();
        invoiceRepository.delete(invoice);
        return toResponse(invoice);
    }
}
