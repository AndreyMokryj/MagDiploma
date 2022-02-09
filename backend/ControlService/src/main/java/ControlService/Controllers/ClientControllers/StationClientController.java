package ControlService.Controllers.ClientControllers;

import ControlService.Entities.StationE;
import ControlService.Repositories.StationRepository;
import ControlService.Repositories.UserRepository;
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
    private UserRepository userRepository;

    @Autowired
    private StationRepository stationRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    Object getAccumulatorByUserId(@PathVariable String userId) {
        String sid = userRepository.findSID(userId);
        Object response = restTemplate.exchange("http://" + sid + "/station/",
                HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/turn-station-{action}/userId/{userId}")
    public @ResponseBody
    boolean turnStationConn(@PathVariable String userId, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }
        String sid = userRepository.findSID(userId);
        StationVO response = restTemplate.exchange("http://" + sid + "/station/turn-station-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<StationVO>() {}).getBody();

        if(!(response == null)){
            StationE accumulator = StationE.fromVO(response);
            stationRepository.save(accumulator);
            return true;
        }
        return false;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/turn-grid-{action}/userId/{userId}")
    public @ResponseBody
    boolean turnGridConn(@PathVariable String userId, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }
        String sid = userRepository.findSID(userId);
        StationVO response = restTemplate.exchange("http://" + sid + "/station/turn-grid-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<StationVO>() {}).getBody();

        if(!(response == null)){
            StationE accumulator = StationE.fromVO(response);
            stationRepository.save(accumulator);
            return true;
        }
        return false;
    }
}
