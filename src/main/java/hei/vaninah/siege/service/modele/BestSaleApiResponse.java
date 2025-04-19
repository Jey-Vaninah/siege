package hei.vaninah.siege.service.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSaleApiResponse {
    private String dishIdentifier;
    private String dishName;
    private Integer quantitySold;
    private Double totalAmount;
}
