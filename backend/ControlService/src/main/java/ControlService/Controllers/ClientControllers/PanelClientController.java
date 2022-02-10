package ControlService.Controllers.ClientControllers;

import ControlService.Entities.PanelE;
import ControlService.Repositories.PanelRepository;
import ControlService.vo.PanelVO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private PanelRepository panelRepository;

    @CrossOrigin(origins = "*")
    @PostMapping(path="/")
    public @ResponseBody
    Iterable<Object> getPanelsByStationId(@RequestBody String sid) {
        List<Object> response = restTemplate.exchange("http://" + sid + "/panels/",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Object>>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/{id}")
    public @ResponseBody
    Object getPanelById(@RequestBody String sid, @PathVariable String id) {
        Object response = restTemplate.exchange("http://" + sid + "/panels/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Object>() {}).getBody();

        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/turn-{action}/{id}")
    public @ResponseBody
    boolean turnPanel(@RequestBody String sid, @PathVariable String id, @PathVariable int action) {
        if(action != 0 && action != 1){
            return false;
        }

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
    @PostMapping(path="/power/{id}")
    public @ResponseBody
    double getPanelPower(@RequestBody String sid, @PathVariable String id) {
        double response = restTemplate.exchange("http://" + sid + "/panels/power/" + id,
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {}).getBody();
        return response;
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/power/total/")
    public @ResponseBody
    double getTotalPower(@RequestBody String sid) {
        double response = restTemplate.exchange("http://" + sid + "/panels/power/",
                HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {}).getBody();
        return response;
    }
}
