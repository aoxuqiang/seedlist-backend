package com.example.seedlist.controller.wechat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.Meeting;
import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.entity.Project;
import com.example.seedlist.entity.ProjectScan;
import com.example.seedlist.entity.User;
import com.example.seedlist.http.WxUser;
import com.example.seedlist.service.BpApplyService;
import com.example.seedlist.service.MeetingApplyService;
import com.example.seedlist.service.MeetingService;
import com.example.seedlist.service.ProjectScanService;
import com.example.seedlist.service.ProjectService;
import com.example.seedlist.service.UserService;
import com.example.seedlist.service.WechatService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("/wx")
public class ApiController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectScanService projectScanService;

    @Autowired
    private BpApplyService bpApplyService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingApplyService meetingApplyService;

    @Autowired
    private UserService userService;

    @Autowired
    private WechatService wechatService;

    private static final String KEY_USER = "UID";


    private void checkUser(String code) {
        WxUser wxUser = wechatService.getWxUser(code);
        if (CharSequenceUtil.isBlank(wxUser.getUserid())) {
            return;
        }
        //保存用户如果不存在的话
        saveUserIfNotExist(wxUser);
    }

    @RequestMapping("/projects")
    public ModelAndView projectList(@RequestParam("code") String code,
                                    @RequestParam(value = "state", required = false) String state) {
        checkUser(code);
        //查询项目信息
        List<Project> projectList = projectService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projects", projectList);
        modelAndView.setViewName("project-list");
        return modelAndView;
    }

    @RequestMapping("/projectDetail")
    public ModelAndView projectList(@RequestParam("id") Integer projectId) {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        //查询项目信息
        Project project = projectService.getById(projectId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("project", project);
        modelAndView.setViewName("project-detail");
        //记录用户浏览数据
        ProjectScan scanRecord = new ProjectScan();
        scanRecord.setProjectId(projectId);
        scanRecord.setUid(uid);
        projectScanService.save(scanRecord);

        return modelAndView;
    }

    @RequestMapping("/projectMeeting")
    public ModelAndView projectMeeting(@RequestParam(value = "code",required = false) String code,
                                       @RequestParam("state") Integer meetingId) {
        if (StrUtil.isNotBlank(code)) {
            checkUser(code);
        }
        Meeting meeting = meetingService.getById(meetingId);
        Project project = projectService.getById(meeting.getProjectId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("meeting", meeting);
        modelAndView.addObject("project", project);
        modelAndView.addObject("disabled", true);
        modelAndView.addObject("showText", "报名");
        modelAndView.setViewName("project-meeting");
        return modelAndView;
    }

    @RequestMapping("/meeting/calendar")
    public ModelAndView projectMeeting(@RequestParam(value = "code") String code) {
        checkUser(code);
        return new ModelAndView("meeting-calendar");
    }

    @RequestMapping("/mineMeeting")
    public ModelAndView mineMeeting(@RequestParam("code") String code) {
        checkUser(code);
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        List<MeetingApply> meetingApplies = meetingApplyService.selectByUser(uid);
        List<Meeting> meetingList = meetingService.findAllById(
                meetingApplies.stream().map(MeetingApply::getMeetingId).collect(Collectors.toList()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("toJoinMeetings", meetingList);
        modelAndView.addObject("joinedMeetings", Lists.newArrayList());
        modelAndView.setViewName("mine-meeting");
        return modelAndView;
    }


    private void saveUserIfNotExist(WxUser wxUser) {
        User user = userService.getByWx(wxUser.getUserid());
        if (user == null) {
            WxUser userInfo = wechatService.getWxUserInfo(wxUser.getUserid());
            user = new User();
            user.setWxUserId(wxUser.getUserid());
            user.setName(userInfo.getName());
            user.setMobile(userInfo.getMobile());
            user.setEmail(userInfo.getEmail());
            userService.save(user);
        }
        request.getSession().setAttribute(KEY_USER, user.getId());
    }

    @GetMapping("/applyBP")
    @ResponseBody
    public Result applyBP(@RequestParam("projectId") Integer projectId) {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        //记录申请记录
        BpApply applyRecord = new BpApply();
        applyRecord.setUid(uid);
        applyRecord.setProjectId(projectId);
        bpApplyService.save(applyRecord);
        return Result.success();
    }

    @GetMapping("/applyMeeting")
    @ResponseBody
    public Result applyMeeting(@RequestParam("meetingId") Integer meetingId) {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        Meeting meeting = meetingService.getById(meetingId);
        if (meetingApplyService.selectUserMeeting(meeting.getId(), uid) == null) {
            MeetingApply applyRecord = new MeetingApply();
            applyRecord.setMeetingId(meetingId);
            applyRecord.setUid(uid);
            applyRecord.setAuditStatus(0);
            meetingApplyService.save(applyRecord);
        }
        return Result.success();
    }

    @GetMapping("/getMonthMeetings")
    @ResponseBody
    public List<Meeting> getMonthMeetings(@RequestParam("date") String dateStr) {
        Date date = DateUtil.parse(dateStr).toJdkDate();
        return meetingService.queryMonthMeetings(date);
    }

    @GetMapping("/scanBP")
    public void scanBP(@RequestParam("pid") Integer pid) {
        Project project = projectService.getById(pid);
        String bpUrl = project.getBp();
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(bpUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(3000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                inputStream = httpURLConnection.getInputStream();
            }
            if (inputStream != null) {
                byte[] buffer = new byte[1024];
                while (inputStream.read(buffer) != -1) {
                    response.getOutputStream().write(buffer);
                    response.getOutputStream().flush();
                }
                response.getOutputStream().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
