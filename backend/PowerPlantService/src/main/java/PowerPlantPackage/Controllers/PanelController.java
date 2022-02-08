package PowerPlantPackage.Controllers;

import PowerPlantPackage.Model.PanelVO;
import PowerPlantPackage.Workflow.WorkProcess;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController    // This means that this class is a Controller
@RequestMapping(path="/panels")
@Component
public class PanelController {
    @GetMapping(path="/")
    public @ResponseBody
    Iterable<PanelVO> getAll() {
        return WorkProcess.getInstance().panels;
    }

    @GetMapping(path="/{id}")
    public @ResponseBody
    PanelVO getById(@PathVariable String id) {
        for (PanelVO panel : WorkProcess.getInstance().panels){
            if (panel.getId().equals(id)){
                return panel;
            }
        }
        return null;
    }

    @GetMapping(path="/turn-{action}/{id}")
    public @ResponseBody
    PanelVO turn(@PathVariable int action, @PathVariable String id) {
        if(action != 0 && action != 1){
            return null;
        }
        for (PanelVO panel : WorkProcess.getInstance().panels){
            if (panel.getId().equals(id)){
                panel.setConnected(action);
                return panel;
            }
        }
        return null;
    }

    @GetMapping(path="/power/{id}")
    public @ResponseBody
    double getPanelPower(@PathVariable String id) {
        for (PanelVO panel : WorkProcess.getInstance().panels){
            if (panel.getId().equals(id)){
                return WorkProcess.getInstance().getPanelPower(panel);
            }
        }
        return 0;
    }

    @GetMapping(path="/power/")
    public @ResponseBody
    double getTotalPower() {
        return WorkProcess.getInstance().getTotalPower();
    }
}
