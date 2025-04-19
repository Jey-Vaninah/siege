package hei.vaninah.siege.service.modele;

import hei.vaninah.siege.entity.DurationUnit;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestProcessingTimeReponse {
    private String salePoint;
    private String dishName;
    private Integer preparationDuration;
    private DurationUnit durationUnit;
}

