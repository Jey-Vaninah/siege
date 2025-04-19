package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalePoint {
    private String id;
    private String name;
    private String apiUrl;
    private String apiKey;
}
