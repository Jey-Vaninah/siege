package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.entity.DurationUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessingTimeRepository {
    private final Connection connection;
    private final SalePointRepository salePointRepository;

    private ProcessingTime resultSetToProcessingTime(ResultSet rs) throws SQLException {
        return new ProcessingTime(
            rs.getString("id"),
            rs.getString("dish_name"),
            DurationUnit.valueOf(rs.getString("duration_unit")),
            rs.getDouble("preparation_duration"),
            rs.getTimestamp("created_at").toLocalDateTime(),
            salePointRepository.findById(rs.getString("id_sale_point"))
        );
    }

    public void save(ProcessingTime processingTime) throws SQLException {
        String query = """
            insert into "processing_time"("id", "dish_name", "duration_unit", "preparation_duration", "created_at", "id_sale_point")
            values (?, ?, ?, ?, ?, ?);
        """;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, processingTime.getId());
        ps.setString(2, processingTime.getDishName());
        ps.setObject(3, processingTime.getDurationUnit(), Types.OTHER);
        ps.setDouble(4, processingTime.getPreparationDuration());
        ps.setTimestamp(5, Timestamp.valueOf(processingTime.getCreatedAt()));
        ps.setString(6, processingTime.getSalePoint().getId());
        ps.executeUpdate();
    }

    public void saveAll(List<ProcessingTime> processingTimes) throws SQLException {
        for (ProcessingTime pt : processingTimes) {
            save(pt);
        }
    }

    public List<ProcessingTime> getAll(Integer top) throws SQLException {
        String query = """
            select * from "processing_time" order by created_at desc limit ?;
        """;

        List<ProcessingTime> processingTimes = new ArrayList<>();
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, top);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            processingTimes.add(resultSetToProcessingTime(rs));
        }

        return processingTimes;
    }
}
