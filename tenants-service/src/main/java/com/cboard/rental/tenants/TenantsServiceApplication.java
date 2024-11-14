package com.cboard.rental.tenants;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.stereotype.Component;

import com.cboard.rental.tenants.config.JWTService;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients
public class TenantsServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TenantsServiceApplication.class, args);
	}

	@Component
	public class TokenValidator {

		private final JWTService jwtService;

		public TokenValidator(JWTService jwtService) {
			this.jwtService = jwtService;
		}

		public boolean isTrustedService(HttpServletRequest request) {
			String authHeader = request.getHeader("Authorization");
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				String token = authHeader.substring(7);
				return jwtService.isTrustedServiceToken(token); // Use the new method
			}
			return false;
		}
	}
	
	@PostConstruct
	public void init() {
	    System.out.println("SPRING_DATASOURCE_URL: " + System.getenv("SPRING_DATASOURCE_URL"));
	    System.out.println("SPRING_DATASOURCE_USERNAME: " + System.getenv("SPRING_DATASOURCE_USERNAME"));
	    System.out.println("SPRING_DATASOURCE_PASSWORD: " + System.getenv("SPRING_DATASOURCE_PASSWORD"));
	}


}


