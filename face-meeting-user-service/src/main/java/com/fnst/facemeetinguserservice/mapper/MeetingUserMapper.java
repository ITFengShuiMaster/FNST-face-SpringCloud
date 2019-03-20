package com.fnst.facemeetinguserservice.mapper;

import com.fnst.facestatic.entity.MeetingUser;
import com.fnst.facestatic.entity.MeetingUserKey;

import java.util.List;

public interface MeetingUserMapper {
    int deleteByPrimaryKey(MeetingUserKey key);

    int insert(MeetingUser record);

    int insertSelective(MeetingUser record);

    MeetingUser selectByPrimaryKey(MeetingUserKey key);

    int updateByPrimaryKeySelective(MeetingUser record);

    int updateByPrimaryKey(MeetingUser record);

    List<MeetingUser> selectByMeetingId(Long meetingId);
}