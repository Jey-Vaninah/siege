package hei.vaninah.siege.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SiegeService {
private final BestSaleService bestSaleService;

    public void synchronization() {
       bestSaleService.synchroniseBestSales();
       //
    }
}
