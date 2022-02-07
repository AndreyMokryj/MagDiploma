package ControlService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class Application1 {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application1.class, args);
    }
}

