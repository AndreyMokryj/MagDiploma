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
    @GetMapping(path="/history-produced/userId/{userId}")
    public @ResponseBody
    Iterable<HistoryProducedLogE> getHistoryProducedLogs(@PathVariable String userId) {
        return historyProducedLogRepository.findByUserId(userId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/history-given/userId/{userId}")
    public @ResponseBody
    Iterable<HistoryGivenLogE> getHistoryGivenLogs(@PathVariable String userId) {
        return historyGivenLogRepository.findByUserId(userId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/today-produced/userId/{userId}")
    public @ResponseBody
    Iterable<TodayProducedLogE> getTodayProducedLogs(@PathVariable String userId) {
        return todayProducedLogRepository.findByUserId(userId);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/today-given/userId/{userId}")
    public @ResponseBody
    Iterable<TodayGivenLogE> getTodayGivenLogs(@PathVariable String userId) {
        return todayGivenLogRepository.findByUserId(userId);
    }
}
