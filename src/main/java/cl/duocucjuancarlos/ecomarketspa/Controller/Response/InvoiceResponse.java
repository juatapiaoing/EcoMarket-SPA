package cl.duocucjuancarlos.ecomarketspa.Controller.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceResponse {
    private int id;
    private String run;
    private String name;
    private List<String> products;
    private List<Integer> prices;
    private Integer total;
}
