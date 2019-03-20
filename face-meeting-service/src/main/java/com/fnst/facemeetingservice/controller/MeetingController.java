package com.fnst.facemeetingservice.controller;

import com.fnst.facemeetingservice.service.MeetingService;
import com.fnst.facestatic.common.EasyUIResponse;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.Meeting;
import com.fnst.facestatic.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meeting")
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	
	@RequestMapping("/list")
	@ResponseBody
	public String list() {
		ServerResponse sr = meetingService.listMeeting();
		EasyUIResponse<Meeting> response = new EasyUIResponse<>();
		List<Meeting> rows = (List<Meeting>)sr.getRows();
		response.setList(rows);
		response.setTotal(rows.size());
		return JsonUtil.objToJson(response).replace("list", "rows");
	}
	
	@GetMapping("")
	public ServerResponse get(@RequestParam("id") Long id) {
		return meetingService.getMeeting(id);
	}
	
	
	@PostMapping("/add")
    public ServerResponse create(Meeting meeting) {
		return meetingService.insertMeeting(meeting);
    }

    @RequestMapping("/delete")
    public ServerResponse delete(@RequestParam("id") Long id){

		return meetingService.deleteMeeting(id);
	}
}
