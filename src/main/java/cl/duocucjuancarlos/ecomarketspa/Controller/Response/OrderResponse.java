package cl.duocucjuancarlos.ecomarketspa.Controller.Response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    private int userId;
    private List<Integer> productId = new ArrayList<>();
}
