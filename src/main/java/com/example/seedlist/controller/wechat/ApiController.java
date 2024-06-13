package com.example.seedlist.controller.wechat;

import cn.hutool.core.collection.CollectionUtil;
import com.example.seedlist.entity.Event;
import com.example.seedlist.entity.Investor;
import com.example.seedlist.entity.Project;
import com.example.seedlist.enums.EventType;
import com.example.seedlist.http.*;
import com.example.seedlist.service.*;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

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
    private InvestorService investorService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EventService eventService;

    @Autowired
    private WechatService wechatService;

    @RequestMapping("/projects")
    public ModelAndView projectList(@RequestParam("code")String code,
                                    @RequestParam(value = "state",required = false) String state) {

        WxUser wxUser = wechatService.getWxUser(code);
//        保存用户如果不存在的话
        saveUserIfNotExist(wxUser);
        request.getSession().setAttribute("userId", wxUser.getUserid());
        request.getSession().setAttribute("userId", "123456");

        //TODO 查询项目信息
        List<Project> projectList = projectService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projects", projectList);
        modelAndView.setViewName("project");
        return modelAndView;
    }

    private void saveUserIfNotExist(WxUser wxUser) {
        Investor investor = investorService.getByWx(wxUser.getUserid());
        if (investor == null) {
            investor = new Investor();
            investor.setWxUserId(wxUser.getUserid());
            investorService.save(investor);
        }
    }

    @GetMapping("/applyBP")
    @ResponseBody
    public void getBP(@RequestParam("projectId") Integer projectId) {
        String userId = (String) request.getSession().getAttribute("userId");
        //记录行为日志
        if (CollectionUtil.isEmpty(eventService.queryUserEvent(userId, EventType.APPLY_BP.getCode()))) {
            Event event = new Event();
            event.setUserid(userId);
            event.setProjectId(projectId);
            event.setEventTime(new Date());
            eventService.save(event);
        }
    }

    @GetMapping("/sendBP")
    @ResponseBody
    public void sendBP(@RequestParam("eventId") Integer eventId) {
        Event event = eventService.getById(eventId);
        Project project = projectService.getById(event.getProjectId());
        String content = String.format("%s的BP\n <a href=\"http://www.dealseedlist.com:8080/wx/scanBP?pid=%s\">请查收</a>",
                project.getBriefName(), project.getId());
        wechatService.sendMessage(Lists.newArrayList(event.getUserid()),content);
    }

    @GetMapping("/scanBP")
    public void scanBP(@RequestParam("pid") Integer pid) {
        Project project = projectService.getById(pid);
        String bpUrl = project.getBpUrl();
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
                int read = 0;
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
