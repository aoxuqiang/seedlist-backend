package com.example.seedlist.http;

import lombok.Data;

@Data
public class WxToken extends WxResponse{

    private String access_token;

    private Long expires_in;
}
