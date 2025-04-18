package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BestSale {
    private String dishName;
    private int quantitySold;
    private double totalAmount;
}
