package StatisticsService.Controllers;

import StatisticsService.Entities.HistoryGivenLogE;
import StatisticsService.Entities.HistoryProducedLogE;
import StatisticsService.Entities.TodayGivenLogE;
import StatisticsService.Entities.TodayProducedLogE;
import StatisticsService.Repositories.HistoryGivenLogRepository;
import StatisticsService.Repositories.HistoryProducedLogRepository;
import StatisticsService.Repositories.TodayGivenLogRepository;
import StatisticsService.Repositories.TodayProducedLogRepository;
import StatisticsService.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/logs")
@Component
public class LogController {
    @Autowired
    private HistoryProducedLogRepository historyProducedLogRepository;

    @Autowired
    private TodayProducedLogRepository todayProducedLogRepository;

    @Autowired
    private HistoryGivenLogRepository historyGivenLogRepository;

    @Autowired
    private TodayGivenLogRepository todayGivenLogRepository;

    @CrossOrigin(origins = "*")
    @PostMapping(path="/history-produced/")
    public @ResponseBody
    Iterable<HistoryProducedLogE> getHistoryProducedLogs(@RequestBody String stationId) {
        return historyProducedLogRepository.findByStationId(stationId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/history-given/")
    public @ResponseBody
    Iterable<HistoryGivenLogE> getHistoryGivenLogs(@RequestBody String stationId) {
        return historyGivenLogRepository.findByStationId(stationId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-produced/")
    public @ResponseBody
    Iterable<TodayProducedLogE> getTodayProducedLogs(@RequestBody String stationId) {
        return todayProducedLogRepository.findByStationId(stationId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-given/")
    public @ResponseBody
    Iterable<TodayGivenLogE> getTodayGivenLogs(@RequestBody String stationId) {
        return todayGivenLogRepository.findByStationId(stationId);
    }

    private HistoryProducedLogE getHistoryProducedLog(LogVO logVO) {
        HistoryProducedLogE historyLog = HistoryProducedLogE.fromVO(logVO);
        try {
            Optional<HistoryProducedLogE> historyLog1 = historyProducedLogRepository.findByParams(
                    historyLog.getStationId(),
                    historyLog.getPanelId(),
                    historyLog.getDate()
            );
            return historyLog1.get();
        }
        catch (Exception ex){
            historyLog.setId(UUID.randomUUID().toString());
            HistoryProducedLogE saved = historyProducedLogRepository.save(historyLog);
            return saved;
        }
    }

    private HistoryGivenLogE getHistoryGivenLog(LogVO logVO) {
        HistoryGivenLogE historyLog = HistoryGivenLogE.fromVO(logVO);
        try {
            Optional<HistoryGivenLogE> historyLog1 = historyGivenLogRepository.findByParams(
                    historyLog.getStationId(),
                    historyLog.getDate()
            );
            return historyLog1.get();
        }
        catch (Exception ex){
            historyLog.setId(UUID.randomUUID().toString());
            HistoryGivenLogE saved = historyGivenLogRepository.save(historyLog);
            return saved;
        }
    }

    private TodayProducedLogE getTodayProducedLog(LogVO logVO) {
        TodayProducedLogE todayLog = TodayProducedLogE.fromVO(logVO);
        try {
            Optional<TodayProducedLogE> todayLog1 = todayProducedLogRepository.findByParams(
                    todayLog.getStationId(),
                    todayLog.getPanelId(),
                    todayLog.getTime()
            );
            return todayLog1.get();
        }
        catch (Exception ex){
            todayLog.setId(UUID.randomUUID().toString());
            TodayProducedLogE saved = todayProducedLogRepository.save(todayLog);
            return saved;
        }
    }

    private TodayGivenLogE getTodayGivenLog(LogVO logVO) {
        TodayGivenLogE todayLog = TodayGivenLogE.fromVO(logVO);
        try {
            Optional<TodayGivenLogE> todayLog1 = todayGivenLogRepository.findByParams(
                    todayLog.getStationId(),
                    todayLog.getTime()
            );
            return todayLog1.get();
        }
        catch (Exception ex){
            todayLog.setId(UUID.randomUUID().toString());
            TodayGivenLogE saved = todayGivenLogRepository.save(todayLog);
            return saved;
        }
    }

    @PostMapping(path="/update/")
    public @ResponseBody
    void updateLogs(@RequestBody LogVO logVO) {
        if (logVO.getPanelId() == null){
            HistoryGivenLogE historyLog = getHistoryGivenLog(logVO);
            historyLog.setGiven(historyLog.getGiven() + logVO.getGiven());
            historyGivenLogRepository.save(historyLog);

            if(logVO.getDateTime().contains("00:00:00")){
                todayGivenLogRepository.deleteByStationId(logVO.getStationId());
                todayProducedLogRepository.deleteByStationId(logVO.getStationId());
            }

            TodayGivenLogE todayLog = getTodayGivenLog(logVO);
            todayLog.setGiven(todayLog.getGiven() + logVO.getGiven());
            todayGivenLogRepository.save(todayLog);
        }
        else {
            HistoryProducedLogE historyLog = getHistoryProducedLog(logVO);
            historyLog.setProduced(historyLog.getProduced() + logVO.getProduced());
            historyProducedLogRepository.save(historyLog);

            TodayProducedLogE todayLog = getTodayProducedLog(logVO);
            todayLog.setProduced(todayLog.getProduced() + logVO.getProduced());
            todayProducedLogRepository.save(todayLog);
        }
    }

    @GetMapping(path="/clear/{stationId}")
    public @ResponseBody
    void clearLogs(@PathVariable String stationId) {
        todayGivenLogRepository.deleteByStationId(stationId);
        todayProducedLogRepository.deleteByStationId(stationId);
        historyGivenLogRepository.deleteByStationId(stationId);
        historyProducedLogRepository.deleteByStationId(stationId);
    }
}
