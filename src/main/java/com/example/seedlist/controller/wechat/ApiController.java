package com.example.seedlist.controller.wechat;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.seedlist.entity.Investor;
import com.example.seedlist.entity.Project;
import com.example.seedlist.entity.Token;
import com.example.seedlist.http.*;
import com.example.seedlist.service.InvestorService;
import com.example.seedlist.service.ProjectService;
import com.example.seedlist.service.TokenService;
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
import java.util.Date;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/wx")
public class ApiController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvestorService investorService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxRequest wxRequest;

    @RequestMapping("/projects")
    public ModelAndView projectList(@RequestParam("code")String code,
                                    @RequestParam(value = "state",required = false) String state) {

        WxUser wxUser = getWxUser(code);
        //保存用户如果不存在的话
        saveUserIfNotExist(wxUser);
        request.getSession().setAttribute("userId", wxUser.getUserid());

        //TODO 查询项目信息
        List<Project> projectList = projectService.getAll();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("projects", projectList);
        modelAndView.addObject("userId", 123);
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

    @GetMapping("/getBP")
    @ResponseBody
    public void getBP(@RequestParam("projectId") Integer projectId) {
        String userId = (String) request.getSession().getAttribute("userId");
        String content = "这是您要的BP\n <a href=\"www.baidu.com\">请查收</a>";
        WxMessage wxMessage = new WxMessage(Lists.newArrayList(userId), content);
        WxSendMsg wxSendMsg = wxRequest.sendMessage(getAccessToken(),wxMessage);
        if (!wxSendMsg.isSuccess()) {
            log.error("发送消息失败,req:{},res:{}",
                    JSONUtil.toJsonStr(wxMessage),JSONUtil.toJsonStr(wxSendMsg));
        }
        log.info("userId:{},要项目{}的BP", userId, projectId);
    }

    private WxUser getWxUser(String code) {
        String token = getAccessToken();
        WxUser userInfo = wxRequest.getUserInfo(token, code);
        log.info("wxUserInfo:{}", JSONUtil.toJsonStr(userInfo));
        return userInfo;
    }


    private String getAccessToken() {
        List<Token> tokens = tokenService.getAll();
        Token token = tokens.get(0);
        if (token.getExpireTime().compareTo(new Date()) <= 0) {
            //获取新的token并更新
            WxToken wxToken = wxRequest.getToken(token.getCorpId(), token.getCorpSecret());
            token.setToken(wxToken.getAccess_token());
            token.setExpireTime(DateUtil.offsetHour(new Date(),2));
            tokenService.save(token);
        }
        return token.getToken();
    }
}
