package PowerPlantPackage.Workflow;

import PowerPlantPackage.Model.StationVO;
import PowerPlantPackage.Model.PanelVO;
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

    @Override
    public void onApplicationEvent(final ServletWebServerInitializedEvent event) {
        String baseUrl = WorkProcess.getInstance().baseUrl;

        StationVO station = null;
        while (station == null) {
            try {
                station = StationVO.fromMap(restTemplate.exchange(baseUrl + "stations/" + serviceName, HttpMethod.GET, null, Map.class).getBody());
                Thread.sleep(10000L);
            } catch (Exception e) {
            }
        }

        WorkProcess.getInstance().station = station;
        WorkProcess.getInstance().setRestTemplate(restTemplate);
        List<Object> objectList = (List<Object>) restTemplate.exchange(baseUrl + "panels/stationId/" + station.getId(), HttpMethod.GET, null, Iterable.class).getBody();
        for (Object object : objectList){
            WorkProcess.getInstance().panels.add(PanelVO.fromMap((Map) object));
        }
        restTemplate.exchange(baseUrl + "logs/clear/" + station.getId(), HttpMethod.GET, null, void.class);
    }
}
