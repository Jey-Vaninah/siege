package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.repository.ProcessingTimeRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProcessingTimeService {
    private final HttpServletService httpServletService;
    private final ProcessingTimeRepository processingTimeRepository;

    private static final String RESTAURANT_SERVER_URL = System.getenv("RESTAURANT_SERVER_URL");
    private static final String RESTAURANT_SERVER_API_KEY = System.getenv("RESTAURANT_SERVER_API_KEY");

    public void synchroniseProcessingTime() {
        LocalDateTime now = LocalDateTime.now();

        ProcessingTime[] processingTimes = httpServletService.doGetProcessingTime(
                RESTAURANT_SERVER_URL + "/processingTimes",
                Map.of("apiKey", RESTAURANT_SERVER_API_KEY)
        );

        System.out.println(Arrays.toString(processingTimes));

        if (processingTimes != null && processingTimes.length > 0) {
            try {
                processingTimeRepository.saveAll(Arrays.asList(processingTimes));
            } catch (SQLException e) {
                System.err.println("Erreur lors de la sauvegarde des ProcessingTime : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
