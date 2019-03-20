package com.fnst.facereport.controller;

import com.fnst.facereport.service.impl.UserService;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Luyue
 * @date 2019/3/20 22:42
 **/
@RestController
@RequestMapping("/report/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ServerResponse list() {
        return userService.list();
    }

    @GetMapping("/{userId}")
    public ServerResponse getUser(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public ServerResponse create(User user) {
        return userService.create(user);
    }

    @PutMapping
    public ServerResponse update(User user) {
        return userService.update(user);
    }

    @DeleteMapping("/{id}")
    public ServerResponse remove(@PathVariable("id") Long id) {
        return userService.delete(id);
    }
}
