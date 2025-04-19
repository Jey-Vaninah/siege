package hei.vaninah.siege.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessingTime {
    private String id;
    private String dishName;
    private double preparationDuration;
    private Duration duration;
}
