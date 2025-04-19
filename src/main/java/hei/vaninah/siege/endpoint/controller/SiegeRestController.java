package hei.vaninah.siege.endpoint.controller;

import hei.vaninah.siege.service.SiegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
public class SiegeRestController {
    private final SiegeService siegeService;


    @GetMapping("/synchronization")
    public ResponseEntity<Object> synchronization() {
        siegeService.synchronization();
        return null;
    }

    @GetMapping("/bestSales")
    public ResponseEntity <Object> getBestSales(@RequestParam Integer top) {
        return null;
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
