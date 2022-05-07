package PowerPlantPackage.Controllers;

import ParallelSolarPanelsPackage.Model.StationDTO;
import ParallelSolarPanelsPackage.WorkProcess;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/station")
@Component
public class StationController {
    @GetMapping(path="/")
    public @ResponseBody
    StationDTO get() {
        return WorkProcess.getInstance().station;
    }

    @GetMapping(path="/turn-station-{action}")
    public @ResponseBody
    StationDTO turnStation(@PathVariable int action) {
        if(action != 0 && action != 1){
            return null;
        }
        WorkProcess.getInstance().station.setStationConnection(action);
        return WorkProcess.getInstance().station;
    }

    @GetMapping(path="/turn-grid-{action}")
    public @ResponseBody
    StationDTO turnGrid(@PathVariable int action) {
        if(action != 0 && action != 1){
            return null;
        }
        WorkProcess.getInstance().station.setGridConnection(action);
        return WorkProcess.getInstance().station;
    }
}
