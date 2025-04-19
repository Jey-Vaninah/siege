package hei.vaninah.siege.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiegeService {
    private final BestSaleService bestSaleService;
    private final ProcessingTimeService processingTimeService;

    public void synchronization() {
       bestSaleService.synchroniseBestSales();
       processingTimeService.synchroniseProcessingTime();
    }
}
