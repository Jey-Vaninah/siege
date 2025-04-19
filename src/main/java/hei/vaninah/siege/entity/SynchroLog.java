package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SynchroLog {
    private String id;
    private Instant updatedAt;
    private SalePoint salePoint;
}
