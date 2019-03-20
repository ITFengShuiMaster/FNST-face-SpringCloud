package com.fnst.facemeetingservice.mapper;


import com.fnst.facestatic.entity.Meeting;

import java.util.List;

public interface MeetingMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Meeting record);

    int insertSelective(Meeting record);

    Meeting selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Meeting record);

    int updateByPrimaryKey(Meeting record);
    
    List<Meeting> selectAll();
    
//    int insertReturnId(Meeting meeting);
}