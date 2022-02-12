package UserService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserService {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(UserService.class, args);
    }
}

