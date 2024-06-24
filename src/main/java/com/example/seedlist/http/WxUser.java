package com.example.seedlist.http;

import lombok.Data;

@Data
public class WxUser extends WxResponse{

    /**
     * 企业内部人员
     */
    private String userid;

    private Long user_ticket;

    /**
     * 企业外部人员
     */
    private String openid;

    private String external_userid;

    private String name;

    private String mobile;

    private String email;
}
