package hei.vaninah.siege.service.modele;

import hei.vaninah.siege.entity.DurationUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProcessingTimeApiResponse {
    private String dishId;
    private String dishName;
    private Double preparationDuration;
    private DurationUnit durationUnit;
}
