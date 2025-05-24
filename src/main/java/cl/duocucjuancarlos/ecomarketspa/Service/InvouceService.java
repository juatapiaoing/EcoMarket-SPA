package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InventoryRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvouceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrderRepository orderRepository;


    public List<InvoiceResponse> getAllInvoices() {
        if (invoiceRepository.getAllInvoiceResponse() == null){
            return null;
        }
        return invoiceRepository.getAllInvoiceResponse();
    }

    public InvoiceRequest addInvoice(InvoiceRequest request) {
        int id = request.getId();
        List<InventoryResponse> listInventory = inventoryRepository.getInventoryResponses();
        List<OrderResponse> listOrder = orderRepository.getAllOrders();
        

    }
}
