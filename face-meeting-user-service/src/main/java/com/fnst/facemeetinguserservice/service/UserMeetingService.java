package com.fnst.facemeetinguserservice.service;

import com.fnst.facemeetinguserservice.mapper.MeetingMapper;
import com.fnst.facemeetinguserservice.mapper.MeetingUserMapper;
import com.fnst.facemeetinguserservice.mapper.UserMapper;
import com.fnst.facestatic.common.CommonVar;
import com.fnst.facestatic.common.ResponseCode;
import com.fnst.facestatic.common.ServerResponse;
import com.fnst.facestatic.entity.MeetingUser;
import com.fnst.facestatic.entity.MeetingUserKey;
import com.fnst.facestatic.entity.User;
import com.fnst.facestatic.util.DateTimeUtil;
import com.fnst.facestatic.util.FaceCompareUtil;
import com.fnst.facestatic.util.JsonUtil;
import com.fnst.facestatic.vo.UserVO;
import com.fnst.facestatic.vo.face.Face;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Luyue
 * @date 2019/3/18 17:04
 **/
@Service
public class UserMeetingService {
    private static final Logger log = LoggerFactory.getLogger(UserMeetingService.class);

    @Value("${img.path}")
    private String imgPath;

    private MeetingUserMapper meetingUserMapper;
    private UserMapper userMapper;
    private MeetingMapper meetingMapper;
    private FileService fileService;

    @Autowired
    public UserMeetingService(MeetingUserMapper meetingUserMapper, UserMapper userMapper, MeetingMapper meetingMapper, FileService fileService) {
        this.meetingUserMapper = meetingUserMapper;
        this.userMapper = userMapper;
        this.meetingMapper = meetingMapper;
        this.fileService = fileService;
    }

    public ServerResponse deleteUserInMeeting(Long meetingId, Long userId) {
        try {
            meetingUserMapper.deleteByPrimaryKey(new MeetingUserKey(meetingId, userId));
            return ServerResponse.success();
        } catch (Exception e) {
            log.error("{} is error", e);
            return ServerResponse.failure(ResponseCode.SYSTEM_INNER_ERROR);
        }
    }

    public ServerResponse insertUserInMeeting(MeetingUser meetingUser) {
        if (userMapper.selectByPrimaryKey(meetingUser.getUserId()) == null) {
            return ServerResponse.failure(ResponseCode.USER_NOT_EXIST);
        }

        if (meetingMapper.selectByPrimaryKey(meetingUser.getMeetingId()) == null) {
            return ServerResponse.failure("没有该场会议");
        }

        meetingUser.setCreateTime(new Date());
        meetingUser.setUpdateTime(new Date());
        meetingUser.setIsAttend(false);
        meetingUser.setIsVisited(false);
        meetingUser.setImgUrl("");
        try {
            if (meetingUserMapper.insert(meetingUser) <= 0) {
                return ServerResponse.failure(ResponseCode.PARAM_IS_INVALID);
            }
        } catch (Exception e) {
            log.error("{} is error", e);
            return ServerResponse.failure(ResponseCode.RESULE_DATA_REPEAT);
        }
        return ServerResponse.success();
    }

    public ServerResponse signIn(String path, String onlineImgFaceToken, String onlineImgFaceBase64_2, Long meetingId) {
        long start = System.nanoTime();

        List<User> users = userMapper.selectAll();
        List<MeetingUser> lists = meetingUserMapper.selectByMeetingId(meetingId);

        long end = System.nanoTime();
        System.out.println("数据库查询++++++++++++++  " + (end - start) / 100000000);
        return checkUser(path, onlineImgFaceToken, users, lists, meetingId, onlineImgFaceBase64_2);
    }

    public ServerResponse listMeetingUser(Long meetingId) {
        if (meetingId == null) {
            return ServerResponse.failure(ResponseCode.PARAM_IS_BLANK);
        }

        if (meetingMapper.selectByPrimaryKey(meetingId) == null) {
            return ServerResponse.failure("没有该场会议");
        }

        List<MeetingUser> lists = meetingUserMapper.selectByMeetingId(meetingId);
        return ServerResponse.success(listRealUser(lists));
    }

    public ServerResponse listOnlyUser(Long meetingId){
        if (meetingId == null) {
            return ServerResponse.failure(ResponseCode.PARAM_IS_BLANK);
        }

        if (meetingMapper.selectByPrimaryKey(meetingId) == null) {
            return ServerResponse.failure("没有该场会议");
        }

        List<MeetingUser> lists = meetingUserMapper.selectByMeetingId(meetingId);
        List<User> userList = Lists.newArrayList();
        for (MeetingUser mUser : lists){
            User user = getUser(mUser.getUserId());
            userList.add(user);
        }
        return ServerResponse.success(userList);
    }

    private UserVO initUserVo(MeetingUser mUser) {
        UserVO userVO = new UserVO();

        User user = getUser(mUser.getUserId());
        userVO.setUser(user);
        userVO.setMeetingUser(mUser);

        return userVO;
    }

    private List<UserVO> listRealUser(List<MeetingUser> lists) {
        List<UserVO> userVOS = Lists.newArrayList();
        List<User> users = userMapper.selectAll();

        for (User user : users) {
            boolean isAdd = false;
            for (int i = 0 ; i < lists.size() ; i ++) {
                if (user.getId().equals(lists.get(i).getUserId())) {
                    userVOS.add(new UserVO(user,lists.get(i)));
                    lists.remove(i);
                    isAdd = true;
                }
            }
            if (!isAdd) {
                userVOS.add(new UserVO(user));
            }
        }
        return userVOS;
    }

    private User getUser(Long id) {
        if (id == null) {
            return null;
        }

        return userMapper.selectByPrimaryKey(id);
    }

    private ServerResponse checkUser(String path, String onlineImgFaceToken, List<User> users, List<MeetingUser> lists, Long meetingId, String onlineImgFaceBase64_2) {
        Double confidence = null;
        UserVO realUserVO = new UserVO();

        long start = System.nanoTime();
        for (User user : users) {
            String resJson = FaceCompareUtil.compareByToken(user.getFaceToken(), onlineImgFaceToken);
            Face face = JsonUtil.json2Object(resJson, Face.class);

            if (face.getConfidence() == null) {
                continue;
            }

            if (confidence == null) {
                confidence = face.getConfidence();
                realUserVO.setUser(user);
            } else {
                if (confidence.compareTo(face.getConfidence()) < 0) {
                    confidence = face.getConfidence();
                    realUserVO.setUser(user);
                }
            }

            if (confidence != null && confidence > CommonVar.CONFIDENCE_RES) {
                break;
            }
        }

        if (confidence == null || confidence < CommonVar.CONFIDENCE_RES) {
            return ServerResponse.failure(ResponseCode.FACE_NONE_IN_FNST);
        }

        long end = System.nanoTime();
        System.out.println("人脸识别++++++++++++++  " + (end - start) / 100000000);

        start = System.nanoTime();
        // 判断是否是访客
        boolean flagToIsVisited = false;
        String fileName = DateTimeUtil.dateToStr(new Date()) + "-online.jpg";
        for (MeetingUser mUser : lists) {
            if (mUser.getUserId().equals(realUserVO.getUser().getId())) {
                flagToIsVisited = true;
                if (!mUser.getIsAttend()) {
                    // 判断是否已签到
                    mUser.setIsAttend(true);
                    // 保存实时图片
                    fileService.saveImgFile(onlineImgFaceBase64_2, path, fileName);
                    if (!StringUtils.isBlank(fileName)) {
                        mUser.setImgUrl(imgPath + fileName);
                    }
                    realUserVO.setMeetingUser(mUser);
                    meetingUserMapper.updateByPrimaryKeySelective(mUser);
                }
                break;
            }
        }
        if (!flagToIsVisited) {
            // 判断是否是访客
            MeetingUser meetingUser = initMeetingUser(meetingId, realUserVO.getUser().getId());
            // 保存实时图片
            fileService.saveImgFile(path, onlineImgFaceBase64_2, fileName);
            if (!StringUtils.isBlank(fileName)) {
                meetingUser.setImgUrl(imgPath + fileName);
            }
            realUserVO.setMeetingUser(meetingUser);
            meetingUserMapper.insert(meetingUser);
        }
        end = System.nanoTime();
        System.out.println("判断是否签到++++++++++++++  " + (end - start) / 100000000);

        return ServerResponse.success(realUserVO);
    }

    private MeetingUser initMeetingUser(Long meetingId, Long userId) {
        MeetingUser meetingUser = new MeetingUser();
        meetingUser.setIsAttend(false);
        meetingUser.setIsVisited(true);
        meetingUser.setImgUrl("");
        meetingUser.setUpdateTime(new Date());
        meetingUser.setCreateTime(new Date());
        meetingUser.setMeetingId(meetingId);
        meetingUser.setUserId(userId);
        return meetingUser;
    }
}
