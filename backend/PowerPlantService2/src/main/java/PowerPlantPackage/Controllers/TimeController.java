package PowerPlantPackage.Controllers;

import ParallelSolarPanelsPackage.WorkProcess.WorkProcess;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/time")
@Component
public class TimeController {
    @GetMapping(path="/")
    public @ResponseBody
    String get() {
        String dateTime = WorkProcess.getInstance().getDateTime();
        return dateTime;
    }
}
