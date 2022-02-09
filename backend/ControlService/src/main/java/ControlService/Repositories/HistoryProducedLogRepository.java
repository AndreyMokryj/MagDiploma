package ControlService.Repositories;

import ControlService.Entities.HistoryProducedLogE;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HistoryProducedLogRepository extends CrudRepository<HistoryProducedLogE, String> {
    @Query("SELECT hl FROM history_produced_logs hl where hl.stationId = :stationId and hl.panelId = :panelId and hl.date = :date")
    @Transactional
    public Optional<HistoryProducedLogE> findByParams(String stationId, String panelId, String date);

    @Query("SELECT hl FROM history_produced_logs hl where hl.stationId = :stationId")
    @Transactional
    public Iterable<HistoryProducedLogE> findByStationId(String stationId);

    @Query("DELETE FROM history_produced_logs hl where hl.stationId = :stationId")
    @Modifying
    @Transactional
    public void deleteByStationId(String stationId);
}
