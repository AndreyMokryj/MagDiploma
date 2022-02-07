package ControlService.Controllers.ClientControllers;

import ControlService.Entities.PanelE;
import ControlService.Repositories.PanelRepository;
import ControlService.Repositories.UserRepository;
import ControlService.vo.PanelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping(path="/app/panels")
@Component
public class PanelClientController {
    @Autowired
    RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PanelRepository panelRepository;

    @CrossOrigin(origins = "*")
    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    Iterable<Object> getPanelsByUserId(@PathVariable String userId) {
        String sid = userRepository.findSID(userId);
        List<Object> response = restTemplate.exchange("http://" + sid + "/panels/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/{id}/userId/{userId}")
    public @ResponseBody
    Object getPanelById(@PathVariable String userId, @PathVariable String id) {
        String sid = userRepository.findSID(userId);
        Object response = restTemplate.exchange("http://" + sid + "/panels/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/turn-{action}/{id}/userId/{userId}")
    public @ResponseBody
    boolean turnPanel(@PathVariable String userId, @PathVariable String id, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }
        String sid = userRepository.findSID(userId);
        PanelVO response = restTemplate.exchange("http://" + sid + "/panels/turn-" + action + "/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<PanelVO>() {}).getBody();

        if(!(response == null)){
            PanelE panel = PanelE.fromVO(response);
            panelRepository.save(panel);
            return true;
        }
        return false;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/power/{id}/userId/{userId}")
    public @ResponseBody
    double getPanelPower(@PathVariable String userId, @PathVariable String id) {
        String sid = userRepository.findSID(userId);
        double response = restTemplate.exchange("http://" + sid + "/panels/power/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {}).getBody();
        return response;
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path="/power/total/userId/{userId}")
    public @ResponseBody
    double getTotalPower(@PathVariable String userId) {
        String sid = userRepository.findSID(userId);
        double response = restTemplate.exchange("http://" + sid + "/panels/power/",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {}).getBody();
        return response;
    }
}
