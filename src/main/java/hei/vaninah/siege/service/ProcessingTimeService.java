package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.CalculationModeType;
import hei.vaninah.siege.entity.DurationUnit;
import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.entity.SalePoint;
import hei.vaninah.siege.repository.ProcessingTimeRepository;
import hei.vaninah.siege.repository.SalePointRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import hei.vaninah.siege.service.modele.BestProcessingTimeApiReponse;
import hei.vaninah.siege.service.modele.BestProcessingTimeReponse;
import hei.vaninah.siege.service.modele.ProcessingTimeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static hei.vaninah.siege.service.httpServlet.HttpServletService.API_KEY_PREFIX;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class ProcessingTimeService {
    private static final String PROCESSING_TIME_URL_PREFIX = "/processingTime";
    private final HttpServletService httpServletService;
    private final ProcessingTimeRepository processingTimeRepository;
    private final SalePointRepository salePointRepository;

    public void synchroniseProcessingTimes() throws SQLException {
        List<SalePoint> salePoints = salePointRepository.getAll();
        List<ProcessingTime> processingTimes = new ArrayList<>();

        for (SalePoint salePoint : salePoints) {
            ProcessingTimeApiResponse[] processingTimeApiResponses = httpServletService.doGetList(
                    salePoint.getApiUrl() + PROCESSING_TIME_URL_PREFIX,
                    Map.of(API_KEY_PREFIX, salePoint.getApiKey()),
                    ProcessingTimeApiResponse[].class
            );
            List<ProcessingTime> newProcessingTimes = Arrays
                    .stream(processingTimeApiResponses)
                    .map(processingTimeApiResponse -> new ProcessingTime(
                        randomUUID().toString(),
                        processingTimeApiResponse.getDishName(),
                        processingTimeApiResponse.getDurationUnit(),
                        processingTimeApiResponse.getPreparationDuration(),
                        LocalDateTime.now(),
                        salePoint
                    ))
                    .toList();
					System.out.println(newProcessingTimes);
            processingTimes.addAll(newProcessingTimes);
        }
        this.processingTimeRepository.saveAll(processingTimes);
    }

    public BestProcessingTimeApiReponse getProcessingTimes(Integer top, CalculationModeType calculationModeType, DurationUnit durationUnit) throws SQLException {
        List<ProcessingTime> all = processingTimeRepository.getAll();

        Map<String, Map<String, List<ProcessingTime>>> grouped = new HashMap<>();
        for (ProcessingTime pt : all) {
            String salePointId = pt.getSalePoint().getId();
            String dishName = pt.getDishName();
            grouped
                    .computeIfAbsent(salePointId, k -> new HashMap<>())
                    .computeIfAbsent(dishName, k -> new ArrayList<>())
                    .add(pt);
        }

        List<BestProcessingTimeReponse> result = new ArrayList<>();
        for (var salePointEntry : grouped.entrySet()) {
            String salePointId = salePointEntry.getKey();
            SalePoint salePoint = salePointRepository.findById(salePointId);

            String bestDish = null;
            double bestValue = calculationModeType == CalculationModeType.MAXIMUM ? Double.MIN_VALUE : Double.MAX_VALUE;
            DurationUnit finalUnit = durationUnit != null ? durationUnit : DurationUnit.MINUTES;
            double selectedDuration = 0;

            for (var dishEntry : salePointEntry.getValue().entrySet()) {
                List<ProcessingTime> times = dishEntry.getValue();
                double value = switch (calculationModeType) {
                    case AVERAGE -> times.stream().mapToDouble(ProcessingTime::getPreparationDuration).average().orElse(0);
                    case MINIMUM -> times.stream().mapToDouble(ProcessingTime::getPreparationDuration).min().orElse(0);
                    case MAXIMUM -> times.stream().mapToDouble(ProcessingTime::getPreparationDuration).max().orElse(0);
                };

                if ((calculationModeType == CalculationModeType.MINIMUM && value < bestValue) ||
                        (calculationModeType == CalculationModeType.MAXIMUM && value > bestValue) ||
                        (calculationModeType == CalculationModeType.AVERAGE && value < bestValue)) {
                    bestValue = value;
                    bestDish = dishEntry.getKey();
                    selectedDuration = value;
                }
            }

            if (bestDish != null) {
                int finalDuration = finalUnit.convertFromMinute(selectedDuration);
                result.add(new BestProcessingTimeReponse(salePoint.getName(), bestDish, finalDuration, finalUnit));
            }
        }

        List<BestProcessingTimeReponse> topList = result.stream()
                .sorted(Comparator.comparingInt(BestProcessingTimeReponse::getPreparationDuration))
                .limit(top)
                .toList();

        return new BestProcessingTimeApiReponse(LocalDateTime.now(), topList);
    }

}
