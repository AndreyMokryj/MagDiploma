package ConnectionService.Controllers;

import ConnectionService.Repositories.PanelRepository;
import ConnectionService.Entities.PanelE;
import ConnectionService.vo.PanelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/panels")
@Component
public class PanelController {
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
}
