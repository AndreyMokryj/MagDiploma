package StatisticsService.Repositories;

import StatisticsService.Entities.TodayGivenLogE;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TodayGivenLogRepository extends CrudRepository<TodayGivenLogE, String> {
    @Query("SELECT tl FROM today_given_logs tl where tl.stationId = :stationId and tl.time = :time")
    @Transactional
    public Optional<TodayGivenLogE> findByParams(String stationId, String time);

    @Query("SELECT tl FROM today_given_logs tl where tl.stationId = :stationId")
    @Transactional
    public Iterable<TodayGivenLogE> findByStationId(String stationId);

    @Query("DELETE FROM today_given_logs tl where tl.stationId = :stationId")
    @Modifying
    @Transactional
    public void deleteByStationId(String stationId);
}
