package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.SynchroLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
@RequiredArgsConstructor
public class SynchroLogRepository {
    private final Connection connection;

    public void save(SynchroLog synchroLog) throws SQLException {
        String query = """
            insert into "synchro_log"("id", "updated_at")
            values (?, ?);
        """;

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, synchroLog.getId());
        ps.setTimestamp(2, Timestamp.valueOf(synchroLog.getUpdatedAt()));
        ps.executeUpdate();
    }
}
