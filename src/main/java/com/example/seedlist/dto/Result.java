package com.example.seedlist.dto;

import com.example.seedlist.enums.ResultCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {

    private int code;
    private String message;
    private Object data;

    public Result(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public Result(int code, String msg, Object data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg());
    }

    public static Result fail(ResultCode resultCode) {
        return new Result(resultCode.getCode(), resultCode.getMsg());
    }

    public static Result success(Object data) {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), data);
    }
}
