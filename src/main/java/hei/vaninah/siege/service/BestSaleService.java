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

        this.bestSaleRepository.saveAll(bestSales);
    }




    public BestDishSaleApiReponse getBestSales(Integer top) throws SQLException {
        List<BestSale> allSales = this.bestSaleRepository.getAll(top);

        Map<String, Map<String, List<BestSale>>> groupedSales = new HashMap<>();
        for (BestSale sale : allSales) {
            String salePointId = sale.getSalePoint().getId();
            String dishName = sale.getDishName();
            groupedSales
                    .computeIfAbsent(salePointId, k -> new HashMap<>())
                    .computeIfAbsent(dishName, k -> new ArrayList<>())
                    .add(sale);
        }

        List<SaleApiReponse> responseList = new ArrayList<>();
        for (var entry : groupedSales.entrySet()) {
            String salePointId = entry.getKey();
            SalePoint salePoint = salePointRepository.findById(salePointId);

            String bestDish = null;
            int maxQuantity = 0;
            double totalAmount = 0;

            for (var dishEntry : entry.getValue().entrySet()) {
                int quantity = dishEntry.getValue().stream().mapToInt(BestSale::getQuantity).sum();
                double amount = dishEntry.getValue().stream().mapToDouble(BestSale::getTotalAmount).sum();
                if (quantity > maxQuantity) {
                    bestDish = dishEntry.getKey();
                    maxQuantity = quantity;
                    totalAmount = amount;
                }
            }

            if (bestDish != null) {
                SaleApiReponse sale = new SaleApiReponse();
                sale.setSalePoint(salePoint.getName());
                sale.setDishName(bestDish);
                sale.setQuantitySold(maxQuantity);
                sale.setTotalAmount(totalAmount);
                responseList.add(sale);
            }
        }

        List<SaleApiReponse> topSales = responseList.stream()
                .sorted(Comparator.comparingInt(SaleApiReponse::getQuantitySold).reversed())
                .limit(top)
                .toList();

        BestDishSaleApiReponse response = new BestDishSaleApiReponse();
        response.setUpdatedAt(LocalDateTime.now());
        response.setSales(topSales);
        return response;
    }

}

