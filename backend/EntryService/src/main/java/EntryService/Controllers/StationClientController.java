package EntryService.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import vo.StationVO;

@RestController
@RequestMapping(path="/app/stations")
@Component
public class StationClientController {
    @Autowired
    RestTemplate restTemplate;

    private String managementUrl = "http://management-service/stations/";

    @CrossOrigin(origins = "*")
    @PostMapping(path="/userId/")
    public @ResponseBody
    Iterable<StationVO> getStationsByUserId(@RequestBody String userId) {
        Iterable<StationVO> response = restTemplate.postForObject(managementUrl + "userId/", userId, Iterable.class);

        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/ukey/{ukey}")
    public @ResponseBody
    StationVO getStationByUkey(@RequestBody String userId, @PathVariable String ukey) {
        StationVO response = restTemplate.postForObject(managementUrl + "ukey/" + ukey, userId, StationVO.class);

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
