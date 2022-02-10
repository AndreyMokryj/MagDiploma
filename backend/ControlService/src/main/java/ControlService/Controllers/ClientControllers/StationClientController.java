package ControlService.Controllers.ClientControllers;

import ControlService.Entities.StationE;
import ControlService.Repositories.StationRepository;
import ControlService.vo.StationVO;
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

    @Autowired
    private StationRepository stationRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    StationE getStationByUserId(@PathVariable String userId) {
        Iterable<StationE> stations = stationRepository.findByUserId(userId);
        StationE result = null;
        for (StationE stationE : stations) {
            result = stationE;
        }
        return result;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/turn-station-{action}")
    public @ResponseBody
    boolean turnStationConn(@RequestBody String sid, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }

        StationVO response = restTemplate.exchange("http://" + sid + "/station/turn-station-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<StationVO>() {}).getBody();

        if(!(response == null)){
            StationE station = StationE.fromVO(response);
            stationRepository.save(station);
            return true;
        }
        return false;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/turn-grid-{action}")
    public @ResponseBody
    boolean turnGridConn(@RequestBody String sid, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }

        StationVO response = restTemplate.exchange("http://" + sid + "/station/turn-grid-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<StationVO>() {}).getBody();

        if(!(response == null)){
            StationE station = StationE.fromVO(response);
            stationRepository.save(station);
            return true;
        }
        return false;
    }
}
