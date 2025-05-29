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

    public InvoiceResponse getInvoiceById(int id) {
        List<InvoiceResponse> invoices = invoiceRepository.getAllInvoiceResponse();
        if (invoices == null) {
            return null;
        }
        for (InvoiceResponse invoice : invoices) {
            if (invoice.getId() == id) {
                return invoice;
            }
        }
        return null;
    }


    public InvoiceResponse addInvoice(InvoiceRequest request) {
        int id = request.getId();
        List<InventoryResponse> listInventory = inventoryRepository.getInventoryResponses();
        List<OrderResponse> listOrder = orderRepository.getAllOrders();
        List<UserResponse> listUser = userRepository.getAllUsers();

        if (listInventory == null || listOrder == null || listUser == null) {
            System.out.println("Alguna lista es nula");
            return null;
        }
        OrderResponse order = null;
        for (OrderResponse o : listOrder) {
            if (o.getUserId() == id) {
                order = o;
                break;
            }
        }
        UserResponse user = null;
        for (UserResponse u : listUser) {
            if (u.getId() == id) {
                user = u;
                break;
            }
        }

        if (order == null || user == null) {
            System.out.println("Order o User es null");
            return null;
        }

        String run = user.getRun();
        String name = user.getFirstName() + " " + user.getLastName();
        List<String> products = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();
        int total = 0;

        for (Integer productId : order.getProductId()) {
            InventoryResponse inv = listInventory.stream()
                    .filter(i -> i.getId() == productId)
                    .findFirst()
                    .orElse(null);
            if (inv != null) {
                products.add(inv.getName());
                prices.add(inv.getPrice());
                total += inv.getPrice();
            }
        }

        if (products.isEmpty()) {
            System.out.println("No se encontraron productos");
            return null;
        }

        InvoiceResponse invoice = new InvoiceResponse();
        invoice.setId(id);
        invoice.setRun(run);
        invoice.setName(name);
        invoice.setProducts(products);
        invoice.setPrices(prices);
        invoice.setTotal(total);

        invoiceRepository.addInvoice(invoice);
        return invoice;
    }
}
