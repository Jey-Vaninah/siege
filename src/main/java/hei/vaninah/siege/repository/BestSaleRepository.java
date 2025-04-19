package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.BestSale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BestSaleRepository {
    private final Connection connection;

    private BestSale resultSetToBestSale(ResultSet rs) throws SQLException {
        return new BestSale(
            rs.getString("id"),
            rs.getString("dish_name"),
            rs.getString("id_dish"),
            rs.getString("id_sale_point"),
            rs.getInt("quantity"),
            rs.getDouble("total_amount"),
            rs.getTimestamp("created_at").toLocalDateTime()
        );
    }

    public void save(BestSale bestSale) throws SQLException {
        String query = """
            insert into "best_sale" ("id", "dish_name","id_dish", "id_sale_point", "quantity", "total_amount", "created_at")
            values (?, ?, ?, ?, ?, ?, ?);
        """;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, bestSale.getId());
        ps.setString(2, bestSale.getDishName());
        ps.setString(3, bestSale.getIdDish());
        ps.setString(4, bestSale.getIdSalePoint());
        ps.setInt(5, bestSale.getQuantity());
        ps.setDouble(6, bestSale.getTotalAmount());
        ps.setTimestamp(7, Timestamp.valueOf(bestSale.getCreatedAt()));
        ps.executeUpdate();
    }

    public void saveAll(List<BestSale> bestSales) throws SQLException {
        for (BestSale bestSale : bestSales) {
            save(bestSale);
        }
    }

    public List<BestSale> getAll(Integer top) throws SQLException {
        String query = """
            select * from "best_sale" order by "created_at" desc limit ?;
        """;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, top);

        ResultSet rs = ps.executeQuery();
        List<BestSale> bestSales = new ArrayList<>();
        while (rs.next()) {
            bestSales.add(resultSetToBestSale(rs));
        }

        return bestSales;
    }
}
