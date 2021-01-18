package com.ivo.javatraing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JavaTraining2Application {

	@LoadBalanced
	@Bean
	public RestTemplate getTemplate() {
		return new RestTemplate();
	}
	public static void main(String[] args) {
		SpringApplication.run(JavaTraining2Application.class, args);
	}

}
