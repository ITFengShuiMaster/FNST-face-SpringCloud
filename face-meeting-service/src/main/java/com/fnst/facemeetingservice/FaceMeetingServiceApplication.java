package com.fnst.facemeetingservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.fnst.facemeetingservice.mapper")
@ServletComponentScan("com.fnst.facemeetingservice.common.filter")
@EnableAsync
public class FaceMeetingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceMeetingServiceApplication.class, args);
	}

}
