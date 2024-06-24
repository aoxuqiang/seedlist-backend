package com.example.seedlist.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.example.seedlist.config.MockConfig;
import com.example.seedlist.entity.Token;
import com.example.seedlist.http.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class WechatService {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private WxRequest wxRequest;

    @Autowired(required = false)
    private MockConfig mockConfig;

    public WxUser getWxUser(String code) {
        if (mockConfig != null) {
            return mockConfig.getWxUser();
        }
        String token = getAccessToken();
        WxUser userInfo = wxRequest.getUser(token, code);
        log.info("wxUserInfo:{}", JSONUtil.toJsonStr(userInfo));
        return userInfo;
    }

    public WxUser getWxUserInfo(String wxUserId) {
        if (mockConfig != null) {
            return mockConfig.getWxUser();
        }
        String token = getAccessToken();
        WxUser wxUser = wxRequest.getUserInfo(token, wxUserId);
        return wxUser;
    }

    public String getAccessToken() {
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


    public void sendMessage(List<String> userIds, String content) {
        if (mockConfig != null) {
            return;
        }
        WxMessage wxMessage = new WxMessage(userIds, content);
        WxSendMsg wxSendMsg = wxRequest.sendMessage(getAccessToken(), wxMessage);
        if (!wxSendMsg.isSuccess()) {
            log.error("发送消息失败,req:{},res:{}",
                    JSONUtil.toJsonStr(wxMessage), JSONUtil.toJsonStr(wxSendMsg));
        }
    }
}
