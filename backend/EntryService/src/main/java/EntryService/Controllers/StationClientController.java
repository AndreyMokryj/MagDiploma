package EntryService.Controllers;

import vo.StationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/app/station")
@Component
public class StationClientController {
    @Autowired
    RestTemplate restTemplate;

    private String managementUrl = "http://management-service/stations/";

    @CrossOrigin(origins = "*")
    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    StationVO getStationByUserId(@PathVariable String userId) {
        StationVO response = restTemplate.exchange(managementUrl + "userId/" + userId,
                HttpMethod.GET, null, new ParameterizedTypeReference<StationVO>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/turn-station-{action}")
    public @ResponseBody
    boolean turnStationConn(@RequestBody String sid, @PathVariable int action) {
        boolean response = restTemplate.postForObject(managementUrl + "turn-station-" + action, sid, Boolean.class);
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/turn-grid-{action}")
    public @ResponseBody
    boolean turnGridConn(@RequestBody String sid, @PathVariable int action) {
        boolean response = restTemplate.postForObject(managementUrl + "turn-grid-" + action, sid, Boolean.class);
        return response;
    }
}
