package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.entity.SalePoint;
import hei.vaninah.siege.repository.ProcessingTimeRepository;
import hei.vaninah.siege.repository.SalePointRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import hei.vaninah.siege.service.modele.ProcessingTimeApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            processingTimes.addAll(newProcessingTimes);
        }

        this.processingTimeRepository.saveAll(processingTimes);
    }

    public List<ProcessingTime> getProcessingTimes(Integer top) throws SQLException {
        return null;
    }
}
