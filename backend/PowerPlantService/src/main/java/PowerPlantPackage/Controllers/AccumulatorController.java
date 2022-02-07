package PowerPlantPackage.Controllers;

import PowerPlantPackage.Model.AccumulatorVO;
import PowerPlantPackage.Workflow.WorkProcess;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/accumulator")
@Component
public class AccumulatorController {
    @GetMapping(path="/")
    public @ResponseBody
    AccumulatorVO get() {
        return WorkProcess.getInstance().accumulator;
    }

    @GetMapping(path="/turn-station-{action}")
    public @ResponseBody
    AccumulatorVO turnStation(@PathVariable int action) {
        if(action != 0 && action != 1){
            return null;
        }
        WorkProcess.getInstance().accumulator.setStationConnection(action);
        return WorkProcess.getInstance().accumulator;
    }

    @GetMapping(path="/turn-grid-{action}")
    public @ResponseBody
    AccumulatorVO turnGrid(@PathVariable int action) {
        if(action != 0 && action != 1){
            return null;
        }
        WorkProcess.getInstance().accumulator.setGridConnection(action);
        return WorkProcess.getInstance().accumulator;
    }
}
