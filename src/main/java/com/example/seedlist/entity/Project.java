package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tb_project")
@Getter
@Setter
public class Project extends BaseEntity {

    /**
     * 项目编号
     */
    private String no;
    /**
     * 项目简称
     */

    private String name;
    /**
     * 公司
     */
    private Integer companyId;
    /**
     * 主打产品
     */
    private String product;
    /**
     * 简述
     */
    private String brief;
    /**
     * 团队
     */
    private String team;
    /**
     * BP地址
     */
    private String bp;
    /**
     * 财务信息;单位(分)
     */
    private Integer finance;
    /**
     * 核心客户
     */
    private String custom;


    private String domain;


    private String competitor;

    /**
     * 项目标签
     */
    private String tags;

    /**
     * 创建人
     */
    @Column(name = "created_by")
    private String createdBy;
    /**
     * 更新人
     */
    @Column(name = "updated_by")
    protected String updatedBy;
}
