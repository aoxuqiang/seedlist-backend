package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_user")
@Entity
@Getter
@Setter
public class User extends BaseEntity {
    private String wxUserId;
    /**
     * 投资机构id
     */
    private Integer orgId;

    private String name;

    private String mobile;

    private String email;
}
