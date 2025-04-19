package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BestSale {
    private String id;
    private String dishName;
    private String idDish;
    private String idSalePoint;
    private Integer quantity;
    private Double totalAmount;
    private LocalDateTime createdAt;
}
