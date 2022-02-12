package EntryService.Controllers;

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

    private String managementUrl = "http://management-service/panels/";

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
        boolean response = restTemplate.postForObject(managementUrl + "turn-" + action + "/" + id, sid, Boolean.class);
        return response;
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
