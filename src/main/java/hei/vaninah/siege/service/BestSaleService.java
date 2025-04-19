package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.BestSale;
import hei.vaninah.siege.entity.SalePoint;
import hei.vaninah.siege.repository.BestSaleRepository;
import hei.vaninah.siege.repository.SalePointRepository;
import hei.vaninah.siege.service.httpServlet.HttpServletService;
import hei.vaninah.siege.service.modele.BestDishSaleApiReponse;
import hei.vaninah.siege.service.modele.BestSaleApiResponse;
import hei.vaninah.siege.service.modele.SaleApiReponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

import static hei.vaninah.siege.service.httpServlet.HttpServletService.API_KEY_PREFIX;
import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class BestSaleService {
    private static final String BEST_SALE_URL_PREFIX = "/sales";
    private final HttpServletService httpServletService;
    private final BestSaleRepository bestSaleRepository;
    private final SalePointRepository salePointRepository;

    public void synchroniseBestSales() throws SQLException {
        List<SalePoint> salePoints = salePointRepository.getAll();
        List<BestSale> bestSales = new ArrayList<>();

        for (SalePoint salePoint : salePoints) {
            BestSaleApiResponse[] bestSaleApiResponses = httpServletService.doGetList(
                salePoint.getApiUrl() + BEST_SALE_URL_PREFIX,
                Map.of(API_KEY_PREFIX, salePoint.getApiKey()),
                BestSaleApiResponse[].class
            );

            List<BestSale> newBestSales = Arrays
                .stream(bestSaleApiResponses)
                .map(bestSaleApiResponse -> new BestSale(
                    randomUUID().toString(),
                    bestSaleApiResponse.getDishName(),
                    bestSaleApiResponse.getDishIdentifier(),
                    salePoint,
                    bestSaleApiResponse.getQuantitySold(),
                    bestSaleApiResponse.getTotalAmount(),
                    LocalDateTime.now()
                ))
                .toList();
            bestSales.addAll(newBestSales);
        }

        bestSaleRepository.saveAll(bestSales);
    }



    public BestDishSaleApiReponse getBestSales(Integer top) throws SQLException {
        List<BestSale> allSales = bestSaleRepository.getAll();

        Map<String, BestSale> bestSaleByDishAndSalePoint = new HashMap<>();
        for (BestSale bestSale : allSales) {
            String key = bestSale.getSalePoint().getId() + "_" + bestSale.getIdDish();
            if (!bestSaleByDishAndSalePoint.containsKey(key) || bestSale.getQuantity() > bestSaleByDishAndSalePoint.get(key).getQuantity()) {
                bestSaleByDishAndSalePoint.put(key, bestSale);
            }
        }

        List<SaleApiReponse> allSalesRest = bestSaleByDishAndSalePoint.values().stream()
                .map(bestSale -> new SaleApiReponse(
                        bestSale.getSalePoint().getName(),
                        bestSale.getDishName(),
                        bestSale.getQuantity(),
                        bestSale.getTotalAmount()
                ))
                .toList();

		List<SaleApiReponse> topSales = allSalesRest.stream()
                .sorted(Comparator.comparingInt(SaleApiReponse::getQuantitySold).reversed())
                .limit(top)
                .toList();

        return new BestDishSaleApiReponse(LocalDateTime.now(), topSales);
    }

}

