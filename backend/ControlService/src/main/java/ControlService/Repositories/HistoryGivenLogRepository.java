package ControlService.Repositories;

import ControlService.Entities.HistoryGivenLogE;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HistoryGivenLogRepository extends CrudRepository<HistoryGivenLogE, String> {
    @Query("SELECT hl FROM history_given_logs hl where hl.stationId = :stationId and hl.date = :date")
    @Transactional
    public Optional<HistoryGivenLogE> findByParams(String stationId, String date);

    @Query("SELECT hl FROM history_given_logs hl where hl.stationId = :stationId")
    @Transactional
    public Iterable<HistoryGivenLogE> findByStationId(String stationId);

    @Query("DELETE FROM history_given_logs hl where hl.stationId = :stationId")
    @Modifying
    @Transactional
    public void deleteByStationId(String stationId);
}
