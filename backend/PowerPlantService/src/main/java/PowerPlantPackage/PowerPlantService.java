package PowerPlantPackage;

import ParallelSolarPanelsPackage.WorkProcess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"ParallelSolarPanelsPackage.*"})
@EnableJpaRepositories(basePackages = "ParallelSolarPanelsPackage.*")
@ComponentScan("ParallelSolarPanelsPackage.*")
@EntityScan("ParallelSolarPanelsPackage.*")
@RestController
public class PowerPlantService {
    public static void main(String[] args) throws Exception {
        System.out.println("Before");
        SpringApplication.run(PowerPlantService.class, args);
        System.out.println("After");

        TimerTask repeatedTask = new TimerTask() {
            public void run() {
                WorkProcess.getInstance().execute();
            }
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

        //Before start
        long delay  = 1000L;

        //Period
        long period = 10000L;
        executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
        Thread.sleep(delay + period * 3);
//        executor.shutdown();
    }
}
