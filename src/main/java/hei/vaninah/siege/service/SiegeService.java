package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.entity.SynchroLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

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

    public List<BestSale> getBestSales(Integer top) {
        try {
            return bestSaleService.getBestSales(top);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
