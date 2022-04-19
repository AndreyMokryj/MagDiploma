package PowerPlantPackage;

import ParallelSolarPanelsPackage.WorkProcess;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@EntityScan("ParallelSolarPanelsPackage.*")
//@RestController
@EnableDiscoveryClient
@SpringBootApplication//(scanBasePackages = {"ParallelSolarPanelsPackage.*"})
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
