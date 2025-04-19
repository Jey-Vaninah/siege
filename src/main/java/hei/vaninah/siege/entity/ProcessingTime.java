package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingTime {
    private String id;
    private String dishName;
    private DurationUnit durationUnit;
    private Double preparationDuration;
    private LocalDateTime createdAt;
    private String idSalePoint;
}
