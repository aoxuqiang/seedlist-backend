package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * 管理员
 */
@Entity
@Table(name = "tb_token")
@Getter
@Setter
public class Token extends BaseEntity {

    private String corpId;

    private String corpSecret;

    private String token;

    private Date expireTime;
}
