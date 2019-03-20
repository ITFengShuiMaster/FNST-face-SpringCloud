package com.fnst.faceuserservice.service;

import com.fnst.facestatic.common.ResponseCode;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.User;
import com.fnst.faceuserservice.mapper.UserMapper;
import com.fnst.faceuserservice.service.async.ImgToFaceTokenAsync;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author Luyue
 * @date 2019/3/17 16:24
 **/
@Service
public class UserService {

    @Value("${img.path}")
    private String imgPath;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private FileService fileService;
    @Autowired
    private ImgToFaceTokenAsync imgToFaceTokenAsync;

    public ServerResponse deleteUser(Long id) {
        if (id == null) {
            return ServerResponse.failure(ResponseCode.PARAM_IS_BLANK);
        }

        if (userMapper.deleteByPrimaryKey(id) <= 0) {
            return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
        }
        return ServerResponse.success();
    }

    public ServerResponse updateUser(User user) {
        // TODO 待测
        user.setUpdateTime(new Date());
        try {
            if (userMapper.updateByPrimaryKeySelective(user) <= 0) {
                return ServerResponse.failure(ResponseCode.PARAM_IS_INVALID);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.failure(ResponseCode.PARAM_IS_INVALID);
        }
        return ServerResponse.success();
    }

    public ServerResponse getUser(Long id) {
        if (id == null) {
            return ServerResponse.failure(ResponseCode.PARAM_IS_BLANK);
        }

        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            return ServerResponse.failure(ResponseCode.USER_NOT_EXIST);
        }
        return ServerResponse.success(user);
    }

    public ServerResponse insertUser(User user, String path) {
        if (!validUserData(user)) {
            return ServerResponse.failure(ResponseCode.PARAM_NOT_COMPLETE);
        }

        if (userMapper.selectByUserNumber(user.getJobNumber()) != null) {
            return ServerResponse.failure(ResponseCode.USER_HAS_EXISTED);
        }

        String fileName = fileService.saveImgFile(user.getImgFile(), path);
        if (StringUtils.isBlank(fileName)) {
            return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
        }

        user.setImgUrl(imgPath + fileName);

        if (userMapper.insertReturnId(user) <= 0) {
            return ServerResponse.failure(ResponseCode.PARAM_IS_INVALID);
        }

        imgToFaceTokenAsync.imgToFaceToken(user, user.getImgUrl());
        return ServerResponse.success();
    }

    private boolean validUserData(User user) {
        if (StringUtils.isBlank(user.getName())) {
            return false;
        }

        if (StringUtils.isBlank(user.getImgUrl())) {
            return false;
        }

        if (StringUtils.isBlank(user.getJobNumber())) {
            return false;
        }

        if (user.getSex() == null) {
            user.setSex(false);
        }

        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return true;
    }

    public ServerResponse listUsers() {
        return ServerResponse.success(userMapper.selectAll());
    }
}
