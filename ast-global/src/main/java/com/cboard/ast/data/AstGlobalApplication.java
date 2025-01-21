package com.cboard.ast.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableScheduling
public class AstGlobalApplication {
    public static void main(String[] args) {
        SpringApplication.run(AstGlobalApplication.class, args);
    }

    /**
     * Define a RestTemplate bean for dependency injection.
     *
     * @return a new RestTemplate instance.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
