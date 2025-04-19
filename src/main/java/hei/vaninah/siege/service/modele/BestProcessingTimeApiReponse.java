package hei.vaninah.siege.service.modele;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BestProcessingTimeApiReponse {
    private LocalDateTime updatedAt;
    private List<BestProcessingTimeReponse> bestProcessingTimes;
}
