package hei.vaninah.siege.endpoint.controller;

import hei.vaninah.siege.entity.DurationUnit;
import hei.vaninah.siege.service.SiegeService;
import hei.vaninah.siege.service.exception.ClientException;
import hei.vaninah.siege.service.exception.NotFoundException;
import hei.vaninah.siege.service.exception.ServerException;
import hei.vaninah.siege.service.modele.BestDishSaleApiReponse;
import hei.vaninah.siege.entity.CalculationModeType;
import hei.vaninah.siege.service.modele.BestProcessingTimeApiReponse;
import hei.vaninah.siege.service.modele.SaleApiReponse;
import lombok.RequiredArgsConstructor;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SiegeController {
	private final SiegeService siegeService;

	@GetMapping("/synchronization")
	public ResponseEntity<Object> synchronization() {
		try {
			siegeService.synchronization();
			return ResponseEntity.ok().body("Synchronization Successfull");
		} catch (ClientException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
		} catch (ServerException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/bestSales")
	public ResponseEntity<Object> getBestSales(@RequestParam Integer top) {
		try {
			BestDishSaleApiReponse response  = siegeService.getBestSales(top);
			return ResponseEntity.ok().body(response);
		} catch (ClientException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
		} catch (ServerException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@GetMapping("/dishes/{id}/bestProcessingTime")
	public ResponseEntity<Object> getProcessingTimes(
			@PathVariable("id") String id,
			@RequestParam Integer top,
			@RequestParam(required = false, defaultValue = "SECONDS") DurationUnit durationUnit,
			@RequestParam(required = false, defaultValue = "AVERAGE") CalculationModeType calculationModeType) {
		try {
			BestProcessingTimeApiReponse response  = siegeService.getBestProcessingTime(top, calculationModeType, durationUnit);
			return ResponseEntity.ok().body(response);
		} catch (ClientException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NotFoundException e) {
			return ResponseEntity.status(NOT_FOUND).body(e.getMessage());
		} catch (ServerException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
