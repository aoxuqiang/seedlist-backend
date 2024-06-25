package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.MeetingMapper;
import com.example.seedlist.dto.MeetingDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.dto.UserMeetingDTO;
import com.example.seedlist.entity.*;
import com.example.seedlist.service.MeetingApplyService;
import com.example.seedlist.service.MeetingService;
import com.example.seedlist.service.ProjectService;
import com.example.seedlist.service.UserService;
import com.example.seedlist.service.WechatService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meeting")
public class MeetingController extends BaseController<MeetingService> {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private UserService userService;
    @Autowired
    private WechatService wechatService;
    private MeetingApplyService meetingApplyService;


    protected MeetingController(MeetingService service) {
        super(service);
    }

    @GetMapping("/list")
    @Cacheable("meetingList")
    public Result meetingList() {
        List<Meeting> meetingList = getService().getAll();
        List<Integer> projectIds = meetingList.stream().map(Meeting::getProjectId).collect(Collectors.toList());
        List<Project> projectList = projectService.findAllById(projectIds);
        Map<Integer, Project> projectMap = projectList.stream().collect(Collectors.toMap(Project::getId, v -> v));
        List<MeetingDTO> result = MeetingMapper.MAPPER.toDTOList(meetingList);
        result.forEach(t -> t.setProjectName(projectMap.get(t.getProjectId()).getName()));
        return success(result);
    }

    @GetMapping("/detail")
    @Cacheable(value = "meetingDetail", key = "#id")
    public Result meetingDetail(@RequestParam("id") Integer id) {
        Meeting meeting = getService().getById(id);
        Project project = projectService.getById(meeting.getProjectId());
        MeetingDTO result = MeetingMapper.MAPPER.toDTO(meeting);
        result.setProjectName(project.getName());
        return success(result);
    }

    @GetMapping("/project")
    public Result listProject(@RequestParam("projectId") Integer projectId) {
        List<Meeting> projectMeetings = getService().selectByProject(projectId);
        return success(projectMeetings);
    }

    @PostMapping("/save")
    @CacheEvict(value = {"meetingList","meetingDetail","monthMeeting"}, allEntries = true)
    public Result saveMeeting(@RequestBody Meeting meeting) {
        getService().save(meeting);
        return success();
    }

    @PostMapping("/invite")
    public Result inviteMeeting(@RequestParam("meetingId") Integer meetingId,
                                @RequestParam("uids") List<Integer> uids) {
        Meeting meeting = getService().getById(meetingId);
        Assert.isTrue(meeting != null, "meeting not found");
        List<User> userList = userService.findAllById(uids);
        List<MeetingInvite> meetingInvites = Lists.newArrayList();
        for (User user : userList) {
            MeetingInvite meetingInvite = new MeetingInvite();
            meetingInvite.setMeetingId(meetingId);
            meetingInvite.setUid(user.getId());
            meetingInvites.add(meetingInvite);
        }
        getService().saveMeetingInviteList(meetingInvites);
        List<String> wxUsers = userList.stream().map(User::getWxUserId).collect(Collectors.toList());
        String redirectUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwd4b9f5c2a07ccc61&redirect_uri=http://www.dealseedlist.com:8080/wx/projectMeeting&response_type=code&state=%d&scope=snsapi_base&agentid=1000011#wechat_redirect", meetingId);
        String content = String.format("【SeedList】向您发送了一个会议邀请，请查收</br> <a href='%s'>【%s】</a>",
                redirectUrl, meeting.getName());
        wechatService.sendMessage(wxUsers, content);
        return success();
    }

    @GetMapping("/apply/list")
    public Result applyList(@RequestParam("meetingId") Integer meetingId) {
        List<MeetingApply> meetingApplies = getService().queryMeetingApplyList(meetingId);
        List<User> userList = userService.findAllById(meetingApplies.stream().map(MeetingApply::getUid).collect(Collectors.toList()));
        Map<Integer, String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<UserMeetingDTO> result = MeetingMapper.MAPPER.toApplyList(meetingApplies);
        result.forEach(t -> t.setUname(map.get(t.getUid())));
        return success(result);
    }

    @GetMapping("/invite/list")
    public Result inviteList(@RequestParam("meetingId") Integer meetingId) {
        List<MeetingInvite> meetingInvites = getService().queryMeetingInviteList(meetingId);
        List<User> userList = userService.findAllById(meetingInvites.stream().map(MeetingInvite::getUid).collect(Collectors.toList()));
        Map<Integer, String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<UserMeetingDTO> result = MeetingMapper.MAPPER.toInviteList(meetingInvites);
        result.forEach(t -> t.setUname(map.get(t.getUid())));
        return success(result);
    }

    @PostMapping("/apply/pass")
    public Result applyPass(@RequestParam("id") Integer id) {
        MeetingApply apply = meetingApplyService.getById(id);
        apply.setAuditStatus(1);
        meetingApplyService.save(apply);
        return success();
    }

    @PostMapping("/apply/refuse")
    public Result applyRefuse(@RequestParam("id") Integer id) {
        MeetingApply apply = meetingApplyService.getById(id);
        apply.setAuditStatus(-1);
        meetingApplyService.save(apply);
        return success();
    }
}
