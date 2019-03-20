package com.fnst.facemeetinguserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.fnst.facemeetinguserservice.mapper")
@ServletComponentScan("com.fnst.facemeetinguserservice.common.filter")
@EnableAsync
public class FaceMeetingUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceMeetingUserServiceApplication.class, args);
	}

}
