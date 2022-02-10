package ControlService.Controllers;

import ControlService.Entities.StationE;
import ControlService.Repositories.StationRepository;
import ControlService.vo.StationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/stations")
@Component
public class StationController {
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
}
