package hei.vaninah.siege.service;

import hei.vaninah.siege.entity.SynchroLog;
import hei.vaninah.siege.repository.SynchroLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
@RequiredArgsConstructor
public class SynchroLogService {
    private final SynchroLogRepository synchroLogRepository;

    public void save(SynchroLog synchroLog) throws SQLException {
        synchroLogRepository.save(synchroLog);
    }
}
