package PowerPlantPackage.Configuration;

import ParallelSolarPanelsPackage.Entities.StateE;
import ParallelSolarPanelsPackage.Repositories.StateRepository;
import ParallelSolarPanelsPackage.Utils.StateUtils;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Configuration
public class MyConfiguration {
    @LoadBalanced
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    StateUtils stateUtils(){
//        return new StateUtils();
//    }
}
