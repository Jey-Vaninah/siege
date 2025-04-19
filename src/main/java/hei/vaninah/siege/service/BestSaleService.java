package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.repository.BestSaleRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BestSaleService {
    private final HttpServletService httpServletService;
    private final BestSaleRepository bestSaleRepository;

    private static final String RESTAURANT_SERVER_URL = System.getenv("RESTAURANT_SERVER_URL");
    private static final String RESTAURANT_SERVER_API_KEY = System.getenv("RESTAURANT_SERVER_API_KEY");

    public void synchroniseBestSales() {
        BestSale[] bestSales = httpServletService.doGetBestSale(
            RESTAURANT_SERVER_URL + "/sales",
            Map.of("apiKey", RESTAURANT_SERVER_API_KEY)
        );

        if (bestSales != null && bestSales.length > 0) {
            try {
                bestSaleRepository.saveAll(Arrays.asList(bestSales));
            } catch (SQLException e) {
                throw new RuntimeException("Failed to save best sales", e);
            }
        }
    }
}

