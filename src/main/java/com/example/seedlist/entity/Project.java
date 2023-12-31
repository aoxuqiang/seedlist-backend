package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "tb_project")
@Getter
@Setter
public class Project extends BaseEntity {

    /**
     * 项目编号
     */
    @Column(name = "project_no")
    private String projectNo;
    /**
     * 项目名称
     */
    @Column(name = "project_name")
    private String projectName;
    /**
     * 公司
     */
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="company_id",referencedColumnName="id")
    private Company company;
    /**
     * 主打产品
     */
    private String product;
    /**
     * 简述
     */
    private String brief;
    /**
     * 团队信息
     */
    private String team;
    /**
     * 财务信息;单位(分)
     */
    private Integer finance;
    /**
     * 备注
     */
    private String remark;

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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_project_tag",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;
}
