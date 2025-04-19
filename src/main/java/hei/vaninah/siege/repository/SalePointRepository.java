package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.SalePoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class SalePointRepository {
    private final Connection connection;

    SalePoint resultSetToSalePoint(ResultSet rs) throws SQLException {
        return new SalePoint(
            rs.getString("id"),
            rs.getString("name"),
            rs.getString("api_url"),
            rs.getString("api_key")
        );
    }


    public SalePoint findById(String id) throws SQLException {
        String query = """
        select * from "sale_point" where "id" = ?;
    """;
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return resultSetToSalePoint(rs);
        }
        return null;
    }


    public List<SalePoint> getAll() throws SQLException {
        List<SalePoint> salePoints = new ArrayList<>();
        String query = """
            select * from "sale_point";
        """;
        PreparedStatement ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            salePoints.add(resultSetToSalePoint(rs));
        }
        return salePoints;
    }
}
