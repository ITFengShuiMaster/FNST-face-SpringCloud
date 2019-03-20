package com.fnst.facezuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class FaceZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceZuulServiceApplication.class, args);
	}

}
