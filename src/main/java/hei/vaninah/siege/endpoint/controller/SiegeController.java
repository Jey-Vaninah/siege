package hei.vaninah.siege.endpoint.controller;

import hei.vaninah.siege.entity.DurationUnit;
import hei.vaninah.siege.service.SiegeService;
import hei.vaninah.siege.service.modele.BestDishSaleApiReponse;
import hei.vaninah.siege.entity.CalculationModeType;
import hei.vaninah.siege.service.modele.BestProcessingTimeApiReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SiegeController {
    private final SiegeService siegeService;

    @GetMapping("/synchronization")
    public void synchronization() {
        siegeService.synchronization();
    }

    @GetMapping("/bestSales")
    public BestDishSaleApiReponse getBestSales(@RequestParam Integer top) {
        return siegeService.getBestSales(top);
    }

    @GetMapping("/dishes/{id}/bestProcessingTime")
    public BestProcessingTimeApiReponse getProcessingTimes(
        @PathVariable("id") String id,
        @RequestParam Integer top,
        @RequestParam(required = false, defaultValue = "SECONDS") DurationUnit durationUnit,
        @RequestParam(required = false, defaultValue = "AVERAGE") CalculationModeType calculationModeType
    ) {
        return siegeService.getBestProcessingTime(top, calculationModeType, durationUnit);
    }
}
