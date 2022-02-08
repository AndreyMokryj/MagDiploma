package ControlService.Controllers.ClientControllers;

import ControlService.Entities.AccumulatorE;
import ControlService.Repositories.AccumulatorRepository;
import ControlService.Repositories.UserRepository;
import ControlService.vo.AccumulatorVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/app/accumulator")
@Component
public class AccumulatorClientController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccumulatorRepository accumulatorRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    Object getAccumulatorByUserId(@PathVariable String userId) {
        String sid = userRepository.findSID(userId);
        Object response = restTemplate.exchange("http://" + sid + "/accumulator/",
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
        AccumulatorVO response = restTemplate.exchange("http://" + sid + "/accumulator/turn-station-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<AccumulatorVO>() {}).getBody();

        if(!(response == null)){
            AccumulatorE accumulator = AccumulatorE.fromVO(response);
            accumulatorRepository.save(accumulator);
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
        AccumulatorVO response = restTemplate.exchange("http://" + sid + "/accumulator/turn-grid-" + action,
                HttpMethod.GET, null, new ParameterizedTypeReference<AccumulatorVO>() {}).getBody();

        if(!(response == null)){
            AccumulatorE accumulator = AccumulatorE.fromVO(response);
            accumulatorRepository.save(accumulator);
            return true;
        }
        return false;
    }
}
