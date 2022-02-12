package ConnectionService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ConnectionService {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ConnectionService.class, args);
    }
}

