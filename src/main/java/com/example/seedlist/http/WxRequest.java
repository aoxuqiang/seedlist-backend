package com.example.seedlist.http;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "wxRequest",url = WxRequest.BASE_URL)
public interface WxRequest {

    public static final String BASE_URL = "https://qyapi.weixin.qq.com/cgi-bin";


    @GetMapping("/gettoken")
    WxToken getToken(@RequestParam("corpid")String corpId,@RequestParam("corpsecret")String corpSecret);

    @GetMapping("/auth/getuserinfo")
    WxUser getUserInfo(@RequestParam("access_token")String accessToken,@RequestParam("code")String code);

    @PostMapping(value = "/message/send",consumes = "application/json")
    WxSendMsg sendMessage(@RequestParam("access_token")String accessToken, @RequestBody WxMessage wxMessage);
}
