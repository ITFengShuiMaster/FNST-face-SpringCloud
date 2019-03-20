package com.fnst.facereport.service;

import com.fnst.facereport.service.hystrix.DataClientServiceHystrix;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "face-zuul-service", fallback = DataClientServiceHystrix.class)
public interface DataService {
    @GetMapping("/service-user/user")
    ServerResponse listUsers();

    @GetMapping("/service-user/user/{id}")
    ServerResponse getUserById(@PathVariable("id") Long id);

    @PostMapping
    ServerResponse create(User user);

    @PutMapping
    ServerResponse update(User user);

    @DeleteMapping("/{id}")
    ServerResponse remove(@PathVariable("id") Long id);
}
