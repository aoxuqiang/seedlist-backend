package com.example.seedlist.dto;

import com.example.seedlist.entity.Company;
import com.example.seedlist.entity.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectDetailDTO implements Serializable {

    private Integer id;

    private String no;

    private String name;

    private Integer companyId;

    private String product;

    private String brief;

    private Integer industryId;

    private String team;

    private String custom;

    private String domain;

    private String competitor;

    private Integer finance;

    private String bp;

    private String remark;

    private String createdBy;

    private String updatedBy;

    private List<Tag> tagList;

    private Company company;

    private Integer show;
}
