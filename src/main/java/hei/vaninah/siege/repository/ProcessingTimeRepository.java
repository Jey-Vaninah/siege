package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.ProcessingTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProcessingTimeRepository {
//    private final Connection connection;

    public List<ProcessingTime> saveAll(List<ProcessingTime> processingTimes){
        throw new RuntimeException("Not implemented");
    }

    public List<ProcessingTime> getAll(List<ProcessingTime> processingTimes){
        throw new RuntimeException("Not implemented");
    }
}
