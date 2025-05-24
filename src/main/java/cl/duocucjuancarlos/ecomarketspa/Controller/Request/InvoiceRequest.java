package cl.duocucjuancarlos.ecomarketspa.Controller.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceRequest {
    private int id;
    private int run;
    private String name;
    private List<String> products;
    private List<Integer> prices;
}
