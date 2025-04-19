package hei.vaninah.siege.endpoint.controller;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.service.SiegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SiegeController {
    private final SiegeService siegeService;

    @GetMapping("/synchronization")
    public void synchronization() {
        siegeService.synchronization();
    }

    @GetMapping("/bestSales")
    public ResponseEntity<List<BestSale>> getBestSales(@RequestParam Integer top) {
        return siegeService.getBestSales(top);
    }

    @GetMapping("/dishes/{id}/processingTime")
    public ResponseEntity <Object> getProcessingTimes(
            @PathVariable("id") String id
//            @RequestParam(required = false, defaultValue = "AVERAGE") ProcessingValueType valueType,
//            @RequestParam(required = false, defaultValue = "SECONDS") ProcessingTimeType timeType
    ) {
        return null;
    }
}
