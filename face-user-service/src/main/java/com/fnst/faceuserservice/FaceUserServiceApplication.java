package com.fnst.faceuserservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEurekaClient
@MapperScan("com.fnst.faceuserservice.mapper")
@ServletComponentScan("com.fnst.faceuserservice.common.filter")
@EnableAsync
public class FaceUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaceUserServiceApplication.class, args);
	}

}
