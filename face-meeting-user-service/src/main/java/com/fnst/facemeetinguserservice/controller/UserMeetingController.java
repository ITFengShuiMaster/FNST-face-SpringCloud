package com.fnst.facemeetinguserservice.controller;

import com.fnst.facemeetinguserservice.service.UserMeetingService;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.MeetingUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luyue
 * @date 2019/3/18 16:02
 **/
@RestController
@RequestMapping("/u_meeting")
public class UserMeetingController {

    @Autowired
    private UserMeetingService userMeetingService;

    @GetMapping("/{meetingId}")
    public ServerResponse list(@PathVariable Long meetingId) {
//        List<User> rows = (List<User>) userMeetingService.listOnlyUser(meetingId).getRows();
//        EasyUIResponse<User> response = new EasyUIResponse<>();
//        response.setTotal(rows.size());
//        response.setList(rows);
//        return JsonUtil.objToJson(response).replace("list","rows");
        return userMeetingService.listMeetingUser(meetingId);
    }

    @PostMapping
    public ServerResponse addUserInMeeting(MeetingUser meetingUser) {
        return userMeetingService.insertUserInMeeting(meetingUser);
    }

    @DeleteMapping("/remove/{meetingId}/{userId}")
    public ServerResponse removeUserInMeeting(@PathVariable Long meetingId, @PathVariable Long userId) {
        return userMeetingService.deleteUserInMeeting(meetingId, userId);
    }

    @PostMapping("/signIn/{meetingId}")
    public ServerResponse signIn(String onlineImgFaceToken, String onlineImgFaceBase64_2, @PathVariable Long meetingId, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        return userMeetingService.signIn(path, onlineImgFaceToken, onlineImgFaceBase64_2, meetingId);
    }
}
