package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 管理员
 */
@Entity
@Table(name = "tb_admin")
@Getter
@Setter
public class Admin extends BaseEntity {
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;
}
