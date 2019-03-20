package com.fnst.facereport.service.hystrix;

import com.fnst.facereport.service.DataService;
import com.fnst.facestatic.common.ResponseCode;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.User;
import org.springframework.stereotype.Component;
/**
 * @author Luyue
 * @date 2019/03/20 0:00
 **/
@Component
public class DataClientServiceHystrix implements DataService {
    @Override
    public ServerResponse listUsers() {
        return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
    }

    @Override
    public ServerResponse getUserById(Long userId) {
        return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
    }

    @Override
    public ServerResponse create(User user) {
        return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
    }

    @Override
    public ServerResponse update(User user) {
        return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
    }

    @Override
    public ServerResponse remove(Long id) {
        return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
    }
}
