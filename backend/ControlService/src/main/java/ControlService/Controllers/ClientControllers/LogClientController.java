package ControlService.Controllers.ClientControllers;

import ControlService.Entities.HistoryGivenLogE;
import ControlService.Entities.HistoryProducedLogE;
import ControlService.Entities.TodayGivenLogE;
import ControlService.Entities.TodayProducedLogE;
import ControlService.Repositories.HistoryGivenLogRepository;
import ControlService.Repositories.HistoryProducedLogRepository;
import ControlService.Repositories.TodayGivenLogRepository;
import ControlService.Repositories.TodayProducedLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/app/logs")
@Component
public class LogClientController {
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
}
