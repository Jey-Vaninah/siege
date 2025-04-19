package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.CalculationModeType;
import hei.vaninah.siege.entity.DurationUnit;
import hei.vaninah.siege.entity.SynchroLog;
import hei.vaninah.siege.service.modele.BestDishSaleApiReponse;
import hei.vaninah.siege.service.modele.BestProcessingTimeApiReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class SiegeService {
    private final BestSaleService bestSaleService;
    private final ProcessingTimeService processingTimeService;
    private final SynchroLogService synchroLogService;

    public void synchronization() {
        try {
            bestSaleService.synchroniseBestSales();
            processingTimeService.synchroniseProcessingTimes();
            synchroLogService.save(new SynchroLog(
                randomUUID().toString(),
                LocalDateTime.now()
            ));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BestDishSaleApiReponse getBestSales(Integer top) {
        try {
            return bestSaleService.getBestSales(top);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public BestProcessingTimeApiReponse getBestProcessingTime(Integer top, CalculationModeType calculationModeType, DurationUnit durationUnit) {
        try {
            return processingTimeService.getProcessingTimes(top, calculationModeType, durationUnit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
