package hei.vaninah.siege.repository;

import hei.vaninah.siege.entity.BestSale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BestSaleRepository {
//    private final Connection connection;

    public List<BestSale> saveAll(List<BestSale> processingTimes){
        throw new RuntimeException("Not implemented");
    }

    public List<BestSale> getAll(List<BestSale> processingTimes){
        throw new RuntimeException("Not implemented");
    }
}
