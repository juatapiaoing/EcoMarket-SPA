package cl.duocucjuancarlos.ecomarketspa.Service;

import cl.duocucjuancarlos.ecomarketspa.Controller.Request.InvoiceRequest;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InventoryResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.InvoiceResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.OrderResponse;
import cl.duocucjuancarlos.ecomarketspa.Controller.Response.UserResponse;
import cl.duocucjuancarlos.ecomarketspa.Repository.InventoryRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.InvoiceRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.OrderRepository;
import cl.duocucjuancarlos.ecomarketspa.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InvoiceService {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;


    public List<InvoiceResponse> getAllInvoices() {
        if (invoiceRepository.getAllInvoiceResponse() == null){
            return null;
        }
        return invoiceRepository.getAllInvoiceResponse();
    }

    public InvoiceResponse addInvoice(InvoiceRequest request) {
        int id = request.getId();
        List<InventoryResponse> listInventory = inventoryRepository.getInventoryResponses();
        List<OrderResponse> listOrder = orderRepository.getAllOrders();
        List<UserResponse> listUser = userRepository.getAllUsers();

        if(id >= 0 && id < listOrder.size() && listOrder != null && listOrder.size() > 0){
            String run = listUser.get(id).getRun();
            String name = listUser.get(id).getFirstName() + " " + listUser.get(id).getLastName();
            List<String> products = new ArrayList<>();
            for (Integer productId : listOrder.get(id).getProductId()) {
                // Busca el nombre del producto en el inventario
                for (InventoryResponse inv : listInventory) {
                    if (inv.getId() == productId) {
                        products.add(inv.getName());
                        break;
                    }
                }
            }
            double total = 0.0;
            for (Integer productId : listOrder.get(id).getProductId()) {
                for (InventoryResponse inv : listInventory) {
                    if (inv.getId() == productId) {
                        products.add(inv.getName());
                        total += inv.getPrice(); // Suma el precio
                        break;
                    }
                }
            }
            InvoiceResponse invoice = new InvoiceResponse(id, run, name, products, total);
            invoiceRepository.addInvoice(invoice);
            return invoice;
        }
        return null;
    }
}
