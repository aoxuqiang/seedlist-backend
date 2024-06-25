package com.example.seedlist.config;

import com.example.seedlist.http.WxUser;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MockConfig {

    public WxUser getWxUser() {
        WxUser wxUser = new WxUser();
        wxUser.setUserid("123456");
        wxUser.setName("axq");
        return wxUser;
    }
}
