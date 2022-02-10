package ControlService.Controllers.ClientControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(path="/app/time")
@Component
public class TimeClientController {
    @Autowired
    RestTemplate restTemplate;

    @CrossOrigin(origins = "*")
    @PostMapping(path="/")
    public @ResponseBody
    String getTime(@RequestBody String sid) {
        String response = restTemplate.exchange("http://" + sid + "/time/",
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}).getBody();

        return response;
    }
}
