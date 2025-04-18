package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.repository.ProcessingTimeRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BestSaleService {
    private final HttpServletService httpServletService;
    private final ProcessingTimeRepository processingTimeRepository;
    private static String RESTAURANT_SERVER_URL = System.getenv("RESTAURANT_SERVER_URL");
    private static String RESTAURANT_SERVER_API_KEY = System.getenv("RESTAURANT_SERVER_API_KEY");

    public void synchroniseBestSales() {
        LocalDateTime now = LocalDateTime.now();
        BestSale[] bestSales = httpServletService.doGet(
            RESTAURANT_SERVER_URL + "/bestSales",
            Map.of("apiKey", RESTAURANT_SERVER_API_KEY)
        );
        System.out.println(Arrays.toString(bestSales));
//        processingTimeRepository.saveAll(bestSales);
    }
}
