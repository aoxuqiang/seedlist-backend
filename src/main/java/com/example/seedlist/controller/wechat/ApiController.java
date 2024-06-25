package com.example.seedlist.controller.wechat;

import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.*;
import com.example.seedlist.http.*;
import com.example.seedlist.service.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    private BpSendService bpSendService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private MeetingApplyService meetingApplyService;

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ProjectScanService eventService;

    @Autowired
    private WechatService wechatService;

    private static final String KEY_USER = "UID";


    private boolean haveUserInfo() {
        return request.getSession().getAttribute(KEY_USER) != null;
    }

    @RequestMapping("/projects")
    public ModelAndView projectList(@RequestParam("code") String code,
                                    @RequestParam(value = "state", required = false) String state) {

        WxUser wxUser = wechatService.getWxUser(code);
        //保存用户如果不存在的话
        saveUserIfNotExist(wxUser);
        request.getSession().setAttribute(KEY_USER, wxUser.getUserid());

        //查询项目信息
        List<Project> projectList = projectService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projects", projectList);
        modelAndView.setViewName("project-list");
        return modelAndView;
    }

    @RequestMapping("/projectDetail")
    public ModelAndView projectList(@RequestParam("id") Integer projectId) {
        //查询项目信息
        Project project = projectService.getById(projectId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("project", project);
        modelAndView.setViewName("project-detail");
        //记录用户浏览数据
        ProjectScan scanRecord = new ProjectScan();
        scanRecord.setProjectId(projectId);
        scanRecord.setUid(scanRecord.getUid());
        projectScanService.save(scanRecord);

        return modelAndView;
    }

    @RequestMapping("/projectMeeting")
    public ModelAndView projectMeeting(@RequestParam(value = "code",required = false) String code,
                                       @RequestParam("state") Integer meetingId) throws IOException {
        if (!haveUserInfo()) {
            String redirectUrl = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wwd4b9f5c2a07ccc61&redirect_uri=http://www.dealseedlist.com:8080/wx/projectMeeting&response_type=code&state=%d&scope=snsapi_base&agentid=1000011#wechat_redirect",meetingId);
            response.sendRedirect("");
            return null;
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

    @RequestMapping("/mineMeeting")
    public ModelAndView mineMeeting() {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        List<MeetingApply> meetingApplies = meetingApplyService.selectByUser(uid);
        List<Meeting> meetingList = meetingService.findAllById(
                meetingApplies.stream().map(MeetingApply::getMeetingId).collect(Collectors.toList()));
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("meetings", meetingList);
        modelAndView.setViewName("mine-meeting");
        return modelAndView;
    }


    private void saveUserIfNotExist(WxUser wxUser) {
        User user = userService.getByWx(wxUser.getUserid());
        if (user == null) {
            WxUser userInfo = wechatService.getWxUserInfo(wxUser.getUserid());
            user = new User();
            user.setWxUserId(wxUser.getUserid());
            user.setName(wxUser.getName());
            user.setMobile(wxUser.getMobile());
            user.setEmail(wxUser.getEmail());
            userService.save(user);
        }
    }

    @GetMapping("/applyBP")
    @ResponseBody
    public Result applyBP(@RequestParam("projectId") Integer projectId) {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        //记录申请记录
        BpApply record = new BpApply();
        record.setUid(uid);
        record.setProjectId(projectId);
        bpApplyService.save(record);
        return Result.success();
    }

    @GetMapping("/applyMeeting")
    @ResponseBody
    public Result applyMeeting(@RequestParam("meetingId") Integer meetingId) {
        Integer uid = (Integer) request.getSession().getAttribute(KEY_USER);
        Meeting meeting = meetingService.getById(meetingId);
        if (meetingApplyService.selectUserMeeting(meeting.getId(), uid) != null) {
            MeetingApply applyRecord = new MeetingApply();
            applyRecord.setMeetingId(meetingId);
            applyRecord.setUid(uid);

            meetingApplyService.save(applyRecord);
        }
        return Result.success();
    }

    @GetMapping("/getMonthMeetings")
    public List<Meeting> getMonthMeetings(@RequestParam("date") Date date) {
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
