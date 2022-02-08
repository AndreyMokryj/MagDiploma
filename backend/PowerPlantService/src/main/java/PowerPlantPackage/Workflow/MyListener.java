package PowerPlantPackage.Workflow;

import PowerPlantPackage.Model.AccumulatorVO;
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
//        RestTemplate restTemplate = WorkProcess.getInstance().getRestTemplate();
        String baseUrl = WorkProcess.getInstance().baseUrl;

        String userId = null;
        while (userId == null) {
            try {
                userId = restTemplate.postForObject(baseUrl + "connect/", serviceName, String.class);
                Thread.sleep(10000L);
            } catch (Exception e) {
            }
        }
        WorkProcess.getInstance().setUserId(userId);
        WorkProcess.getInstance().setRestTemplate(restTemplate);
        WorkProcess.getInstance().accumulator = AccumulatorVO.fromMap(restTemplate.exchange(baseUrl + "accumulators/" + userId, HttpMethod.GET, null, Map.class).getBody());
        List<Object> objectList = (List<Object>) restTemplate.exchange(baseUrl + "panels/", HttpMethod.GET, null, Iterable.class).getBody();
        for (Object object : objectList){
            WorkProcess.getInstance().panels.add(PanelVO.fromMap((Map) object));
        }
        restTemplate.exchange(baseUrl + "logs/clear/" + userId, HttpMethod.GET, null, void.class);
    }
}
