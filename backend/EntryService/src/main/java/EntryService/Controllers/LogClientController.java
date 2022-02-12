package EntryService.Controllers;

import EntryService.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/app/logs")
@Component
public class LogClientController {
    @Autowired
    RestTemplate restTemplate;

    private String statisticsUrl = "http://statistics-service/logs/";

    @CrossOrigin(origins = "*")
    @PostMapping(path="/history-produced/")
    public @ResponseBody
    Iterable<LogVO> getHistoryProducedLogs(@RequestBody String stationId) {
        Iterable<LogVO> response = restTemplate.postForObject( statisticsUrl + "history-produced/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/history-given/")
    public @ResponseBody
    Iterable<LogVO> getHistoryGivenLogs(@RequestBody String stationId) {
        Iterable<LogVO> response = restTemplate.postForObject( statisticsUrl + "history-given/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-produced/")
    public @ResponseBody
    Iterable<LogVO> getTodayProducedLogs(@RequestBody String stationId) {
        Iterable<LogVO> response = restTemplate.postForObject( statisticsUrl + "today-produced/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-given/")
    public @ResponseBody
    Iterable<LogVO> getTodayGivenLogs(@RequestBody String stationId) {
        Iterable<LogVO> response = restTemplate.postForObject( statisticsUrl + "today-given/", stationId, Iterable.class);
        return response;
    }
}
