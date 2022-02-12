package PowerPlantPackage.Utils;

import PowerPlantPackage.Entities.StateE;
import PowerPlantPackage.Repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import vo.PanelVO;
import vo.PreviousVO;
import vo.StateVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class StateUtils {
    @Autowired
    StateRepository stateRepository;

    public StateE fetchState(StateVO stateVO) {
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

    public void updatePrevState(PreviousVO previousVO) {
        StateE state = getById(previousVO.getId());
        state.setAltPlus(state.getAltPlus() + previousVO.getAltPlus());
        state.setAltMinus(state.getAltMinus() + previousVO.getAltMinus());
        state.setAzPlus(state.getAzPlus() + previousVO.getAzPlus());
        state.setAzMinus(state.getAzMinus() + previousVO.getAzMinus());
        stateRepository.save(state);
    }

    StateE getById(String id) {
        Optional<StateE> direction = stateRepository.findById(id);
        return direction.get();
    }

    public void reduceByPanelId(@PathVariable String panelId) {
        int k = 10;

        List<StateE> states = (List<StateE>) stateRepository.findByPanelId(panelId);
        for (StateE state : states) {
            state.setAzPlus(state.getAzPlus() / k);
            state.setAzMinus(state.getAzMinus() / k);
            state.setAltPlus(state.getAltPlus() / k);
            state.setAltMinus(state.getAltMinus() / k);
        }

        stateRepository.saveAll(states);
    }

    public void preparePanel(PanelVO panel) {
        StateVO stateVO = new StateVO();
        stateVO.setPanelId(panel.getId());
        stateVO.setAzimuth(panel.getAzimuth());
        stateVO.setAltitude(panel.getAltitude());

        StateE currentState = fetchState(stateVO);
        currentState.setAzPlus(0.00001);
        currentState.setAzMinus(0);
        currentState.setAltPlus(0);
        currentState.setAltMinus(0);
        stateRepository.save(currentState);

        stateVO.setAzimuth((stateVO.getAzimuth() + 1) % 360);
        StateE nextState = fetchState(stateVO);
        nextState.setAzPlus(0);
        nextState.setAzMinus(0);
        nextState.setAltPlus(0);
        nextState.setAltMinus(0);
        stateRepository.save(nextState);
    }
}
