package com.example.seedlist.http;


import lombok.Data;

@Data
public class WxSendMsg extends WxResponse {

    private String invaliduser;

    private String invalidparty;

    private String invalidtag;

    private String unlicenseduser;

    private String msgid;

    private String response_code;
}
