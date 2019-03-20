package com.fnst.facereport.service.impl;

import com.fnst.facereport.service.DataService;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luyue
 * @date 2019/3/20 22:42
 **/
@Service
public class UserService {

    @Autowired
    private DataService dataService;

    public ServerResponse list() {
        return dataService.listUsers();
    }

    public ServerResponse getUserById(Long userId) {
        return dataService.getUserById(userId);
    }

    public ServerResponse create(User user) {
        return dataService.create(user);
    }

    public ServerResponse update(User user) {
        return dataService.update(user);
    }

    public ServerResponse delete(Long id) {
        return dataService.remove(id);
    }
}
