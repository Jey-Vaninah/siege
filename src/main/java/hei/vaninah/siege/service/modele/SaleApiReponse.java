package hei.vaninah.siege.service.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaleApiReponse {
    private String salePoint;
    private String dishName;
    private Integer quantitySold;
    private Double totalAmount;
}
