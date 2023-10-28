package com.example.seedlist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResultCode {
    SUCCESS(20000, "success"),
    AUTH_FAIL(30000, "用户名/密码错误"),
    PARAM_ERROR(40000, "参数校验异常"),
    ;

    private final int code;
    private final String msg;
}