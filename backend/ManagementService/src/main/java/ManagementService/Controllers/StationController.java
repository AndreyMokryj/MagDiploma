package ManagementService.Controllers;

import ManagementService.Entities.StationE;
import ManagementService.Repositories.StationRepository;
import ParallelSolarPanelsPackage.Model.StationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping(path="/stations")
@Component
public class StationController {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private StationRepository stationRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<StationE> getAll() {
        return stationRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    StationE getById(@PathVariable String id) {
        Optional<StationE> station = stationRepository.findById(id);
        return station.get();
    }

    @PostMapping(path="/{id}")
    public @ResponseBody
    void updateById(@PathVariable String id, @RequestBody StationVO stationVO) {
        StationE station = StationE.fromVO(stationVO);
        stationRepository.save(station);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/userId/")
    public @ResponseBody
    Iterable<StationE> getByUserId(@RequestBody String userId) {
        return stationRepository.findByUserId(userId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path="/ukey/{ukey}")
    public @ResponseBody
    StationE getByUkey(@RequestBody String userId, @PathVariable String ukey) {
        Optional<StationE> station =  stationRepository.findByUkey(userId, ukey);
        return station.get();
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
