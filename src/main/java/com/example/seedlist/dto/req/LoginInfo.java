package com.example.seedlist.dto.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录信息
 */
@Data
public class LoginInfo {
    /**
     * 账号
     */
    @NotBlank(message = "账号不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;
}
