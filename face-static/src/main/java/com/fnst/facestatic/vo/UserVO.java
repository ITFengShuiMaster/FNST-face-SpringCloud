package com.fnst.facestatic.vo;


import com.fnst.facestatic.entity.MeetingUser;
import com.fnst.facestatic.entity.User;

/**
 * @author Luyue
 * @date 2019/3/19 10:58
 **/
public class UserVO {

    private User user;
    private MeetingUser meetingUser;

    public UserVO() {

    }

    public UserVO(User user) {
        this.user = user;
        this.meetingUser = null;
    }

    public UserVO(User user, MeetingUser meetingUser) {
        this.user = user;
        this.meetingUser = meetingUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingUser getMeetingUser() {
        return meetingUser;
    }

    public void setMeetingUser(MeetingUser meetingUser) {
        this.meetingUser = meetingUser;
    }
}
