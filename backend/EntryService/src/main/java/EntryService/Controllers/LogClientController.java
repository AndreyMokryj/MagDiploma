package EntryService.Controllers;

import ParallelSolarPanelsPackage.Model.LogDTO;
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
    Iterable<LogDTO> getHistoryProducedLogs(@RequestBody String stationId) {
        Iterable<LogDTO> response = restTemplate.postForObject( statisticsUrl + "history-produced/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/history-given/")
    public @ResponseBody
    Iterable<LogDTO> getHistoryGivenLogs(@RequestBody String stationId) {
        Iterable<LogDTO> response = restTemplate.postForObject( statisticsUrl + "history-given/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-produced/")
    public @ResponseBody
    Iterable<LogDTO> getTodayProducedLogs(@RequestBody String stationId) {
        Iterable<LogDTO> response = restTemplate.postForObject( statisticsUrl + "today-produced/", stationId, Iterable.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/today-given/")
    public @ResponseBody
    Iterable<LogDTO> getTodayGivenLogs(@RequestBody String stationId) {
        Iterable<LogDTO> response = restTemplate.postForObject( statisticsUrl + "today-given/", stationId, Iterable.class);
        return response;
    }
}
