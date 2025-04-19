package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.ProcessingTime;
import hei.vaninah.siege.entity.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessingTimeRepository {
    private final Connection connection;

    private ProcessingTime resultSetToProcessingTime(ResultSet rs) throws SQLException {
        return new ProcessingTime(
                rs.getString("id"),
                rs.getString("dish_name"),
                rs.getDouble("preparation_duration"),
                Duration.valueOf(rs.getString("duration"))
        );
    }

    public ProcessingTime save(ProcessingTime processingTime) throws SQLException {
        String query = """
            INSERT INTO "processing_time" ("id", "dish_name", "preparation_duration", "duration")
            VALUES (?, ?, ?, ?);
        """;

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, processingTime.getId());
            ps.setString(2, processingTime.getDishName());
            ps.setDouble(3, processingTime.getPreparationDuration());
            ps.setString(4, processingTime.getDuration().name());
            ps.executeUpdate();
        }

        return processingTime;
    }

    public List<ProcessingTime> saveAll(List<ProcessingTime> processingTimes) throws SQLException {
        for (ProcessingTime pt : processingTimes) {
            save(pt);
        }
        return processingTimes;
    }

    public List<ProcessingTime> getAll() throws SQLException {
        String query = """
            SELECT * FROM "processing_time";
        """;

        List<ProcessingTime> processingTimes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                processingTimes.add(resultSetToProcessingTime(rs));
            }
        }

        return processingTimes;
    }
}
