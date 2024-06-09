package com.example.seedlist.http;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxResponse implements Serializable {

    private Integer errcode;

    private String errmsg;


    public boolean isSuccess() {
        return errcode == 0;
    }
}
