package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.BestSale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BestSaleRepository {
    private final Connection connection;

    private BestSale resultSetToBestSale(ResultSet rs) throws SQLException {
        return new BestSale(
                rs.getString("dish_name"),
                rs.getInt("quantity_sold"),
                rs.getDouble("total_amount")
        );
    }

    public BestSale save(BestSale bestSale) throws SQLException {
        String query = """
            INSERT INTO "best_sale" ("dish_name", "quantity_sold", "total_amount")
            VALUES (?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, bestSale.getDishName());
            ps.setInt(2, bestSale.getQuantitySold());
            ps.setDouble(3, bestSale.getTotalAmount());
            ps.executeUpdate();
        }

        return bestSale;
    }

    public List<BestSale> saveAll(List<BestSale> bestSales) throws SQLException {
        for (BestSale bestSale : bestSales) {
            save(bestSale);
        }
        return bestSales;
    }

    public List<BestSale> getAll() throws SQLException {
        String query = """
            SELECT * FROM "best_sale";
        """;
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        List<BestSale> bestSales = new ArrayList<>();
        while (rs.next()) {
            bestSales.add(resultSetToBestSale(rs));
        }

        return bestSales;
    }
}
