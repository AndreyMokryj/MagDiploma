package ManagementService.Controllers;

import ManagementService.Repositories.PanelRepository;
import ManagementService.Entities.PanelE;
import ManagementService.vo.PanelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping(path="/panels")
@Component
public class PanelController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private PanelRepository panelRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<PanelE> getAll() {
        return panelRepository.findAll();
    }

    @GetMapping(path="/stationId/{stationId}")
    public @ResponseBody
    Iterable<PanelE> getByStationId(@PathVariable String stationId) {
        return panelRepository.findByStationId(stationId);
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    PanelE getById(@PathVariable String id) {
        Optional<PanelE> panel = panelRepository.findById(id);
        return panel.get();
    }

    @PostMapping(path="/{id}")
    public @ResponseBody
    void updateById(@PathVariable String id, @RequestBody PanelVO panelVO) {
        PanelE panel = PanelE.fromVO(panelVO);
        panelRepository.save(panel);
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
}
