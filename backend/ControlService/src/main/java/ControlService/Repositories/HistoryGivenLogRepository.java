package ControlService.Repositories;

import ControlService.Entities.HistoryGivenLogE;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface HistoryGivenLogRepository extends CrudRepository<HistoryGivenLogE, String> {
    @Query("SELECT hl FROM history_given_logs hl where hl.userId = :userId and hl.date = :date")
    @Transactional
    public Optional<HistoryGivenLogE> findByParams(String userId, String date);

    @Query("SELECT hl FROM history_given_logs hl where hl.userId = :userId")
    @Transactional
    public Iterable<HistoryGivenLogE> findByUserId(String userId);

    @Query("DELETE FROM history_given_logs hl where hl.userId = :userId")
    @Modifying
    @Transactional
    public void deleteByUserId(String userId);
}
