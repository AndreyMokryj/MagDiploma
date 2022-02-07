package ControlService.Controllers;

import ControlService.Entities.StateE;
import ControlService.Repositories.StateRepository;
import ControlService.vo.StateVO;
import ControlService.vo.PreviousVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/states")
@Component
public class StateController {
    @Autowired
    private StateRepository stateRepository;

    @GetMapping(path="/")
    public @ResponseBody
    Iterable<StateE> getAll() {
        return stateRepository.findAll();
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    StateE getById(@PathVariable String id) {
        Optional<StateE> direction = stateRepository.findById(id);
        return direction.get();
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

    @PostMapping("/get/")
    public StateE getSt(@RequestBody StateVO stateVO) {
        StateE state = getState(stateVO);
        return state;
    }

    @PostMapping("/update/")
    public void updatePrevState(@RequestBody PreviousVO previousVO) {
        StateE state = getById(previousVO.getId());
        state.setAltPlus(state.getAltPlus() + previousVO.getAltPlus());
        state.setAltMinus(state.getAltMinus() + previousVO.getAltMinus());
        state.setAzPlus(state.getAzPlus() + previousVO.getAzPlus());
        state.setAzMinus(state.getAzMinus() + previousVO.getAzMinus());
        stateRepository.save(state);
    }

    @PostMapping("/change/")
    public void changeState(@RequestBody StateVO stateVO) {
        StateE state = getById(stateVO.getId());
        state.setAltPlus(stateVO.getAltPlus());
        state.setAltMinus(stateVO.getAltMinus());
        state.setAzPlus(stateVO.getAzPlus());
        state.setAzMinus(stateVO.getAzMinus());
        stateRepository.save(state);
    }
}
