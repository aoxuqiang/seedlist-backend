package com.example.seedlist.controller;

import com.example.seedlist.dto.Result;
import com.example.seedlist.service.BaseService;

/**
 * 这里定义一些公用的方法
 */
public abstract class BaseController<S extends BaseService> {

    private final S service;


    protected BaseController(S service) {
        this.service = service;
    }

    protected static Result success() {
        return Result.success();
    }

    protected S getService() {
        return this.service;
    }

    protected static Result success(Object data) {
        return Result.success(data);
    }
}
