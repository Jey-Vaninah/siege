package hei.vaninah.siege.service.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestDishSaleApiReponse {
    private LocalDateTime updatedAt;
    private List<SaleApiReponse> sales;
}
