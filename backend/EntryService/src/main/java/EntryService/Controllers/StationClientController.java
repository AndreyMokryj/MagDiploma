package EntryService.Controllers;

import ParallelSolarPanelsPackage.Model.StationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    Iterable<StationDTO> getStationsByUserId(@RequestBody String userId) {
        Iterable<StationDTO> response = restTemplate.postForObject(managementUrl + "userId/", userId, Iterable.class);

        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/ukey/{ukey}")
    public @ResponseBody
    StationDTO getStationByUkey(@RequestBody String userId, @PathVariable String ukey) {
        StationDTO response = restTemplate.postForObject(managementUrl + "ukey/" + ukey, userId, StationDTO.class);

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
