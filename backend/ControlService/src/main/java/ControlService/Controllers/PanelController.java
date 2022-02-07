package ControlService.Controllers;

import ControlService.Entities.PanelE;
import ControlService.Entities.StateE;
import ControlService.Repositories.PanelRepository;
import ControlService.Repositories.StateRepository;
import ControlService.vo.PanelVO;
import ControlService.vo.StateVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/panels")
@Component
public class PanelController {
    @Autowired
    private PanelRepository panelRepository;

    @Autowired
    private StateRepository stateRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<PanelE> getAll() {
        return panelRepository.findAll();
    }

    @GetMapping(path="/userId/{userId}")
    public @ResponseBody
    Iterable<PanelE> getByUserId(@PathVariable String userId) {
        return panelRepository.findByUserId(userId);
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

    @GetMapping("/reduce/{panelId}")
    public void reduceForPanel(@PathVariable String panelId) {
        int k = 10;

        List<StateE> states = (List<StateE>) stateRepository.findByPanelId(panelId);
        PanelE panel = getById(panelId);
        for (StateE state : states) {
            state.setAzPlus(state.getAzPlus() / k);
            state.setAzMinus(state.getAzMinus() / k);
            state.setAltPlus(state.getAltPlus() / k);
            state.setAltMinus(state.getAltMinus() / k);
        }

        stateRepository.saveAll(states);

    }

    private StateE getState(StateVO stateVO) {
        StateE state = StateE.fromVO(stateVO);
        try {
            Optional<StateE> state1 = stateRepository.findByParams(
                    state.getPanelId(),
                    state.getAzimuth(),
                    state.getAltitude()
            );
            return state1.get();
        }
        catch (Exception ex){
            state.setId(UUID.randomUUID().toString());
            StateE saved = stateRepository.save(state);
            return saved;
        }
    }

    @GetMapping("/prepare/{panelId}")
    public void preparePanel(@PathVariable String panelId) {
        PanelE panel = getById(panelId);

        StateVO stateVO = new StateVO();
        stateVO.setPanelId(panelId);
        stateVO.setAzimuth(panel.getAzimuth());
        stateVO.setAltitude(panel.getAltitude());

        StateE currentState = getState(stateVO);
        currentState.setAzPlus(0.00001);
        currentState.setAzMinus(0);
        currentState.setAltPlus(0);
        currentState.setAltMinus(0);
        stateRepository.save(currentState);

        stateVO.setAzimuth((stateVO.getAzimuth() + 1) % 360);
        StateE nextState = getState(stateVO);
        nextState.setAzPlus(0);
        nextState.setAzMinus(0);
        nextState.setAltPlus(0);
        nextState.setAltMinus(0);
        stateRepository.save(nextState);
    }
}
