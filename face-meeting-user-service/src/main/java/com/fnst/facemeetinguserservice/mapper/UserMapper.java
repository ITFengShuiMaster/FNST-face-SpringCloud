package com.fnst.facemeetinguserservice.mapper;

import com.fnst.facestatic.entity.User;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int insertReturnId(User user);

    User selectByUserNumber(String userNumber);

    List<User> selectAll();
}