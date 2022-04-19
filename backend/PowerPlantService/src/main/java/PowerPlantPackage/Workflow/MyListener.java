package PowerPlantPackage.Workflow;

import ParallelSolarPanelsPackage.Model.PanelVO;
import ParallelSolarPanelsPackage.Model.StationVO;
import ParallelSolarPanelsPackage.Utils.StateUtils;
import ParallelSolarPanelsPackage.WorkProcess;
import PowerPlantPackage.Repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Component
public class MyListener implements ApplicationListener<ServletWebServerInitializedEvent> {
    @Value("${spring.application.name}")
    private String serviceName;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    StateRepository stateRepository;

    @Override
    public void onApplicationEvent(final ServletWebServerInitializedEvent event) {
//        String baseUrl = WorkProcess.getInstance().baseUrl;
        String managementUrl = WorkProcess.getInstance().managementUrl;
        String statisticsUrl = WorkProcess.getInstance().statisticsUrl;

        StationVO station = null;
        while (station == null) {
            try {
                station = StationVO.fromMap(restTemplate.exchange(managementUrl + "stations/" + serviceName, HttpMethod.GET, null, Map.class).getBody());
                Thread.sleep(10000L);
            } catch (Exception e) {
            }
        }

        WorkProcess.getInstance().station = station;
        WorkProcess.getInstance().setRestTemplate(restTemplate);
        StateUtils stateUtils = new StateUtils(stateRepository);

        WorkProcess.getInstance().setStateUtils(stateUtils);
        List<Object> objectList = (List<Object>) restTemplate.exchange(managementUrl + "panels/stationId/" + station.getId(), HttpMethod.GET, null, Iterable.class).getBody();
        for (Object object : objectList){
            WorkProcess.getInstance().panels.add(PanelVO.fromMap((Map) object));
        }
        try {
            restTemplate.exchange(statisticsUrl + "logs/clear/" + station.getId(), HttpMethod.GET, null, void.class);
        } catch (Exception e) {
            System.out.println("--------WARNING: Could not update statistics at initialization----------");
        }
    }
}
