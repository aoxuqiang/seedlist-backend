package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectDTO implements Serializable {

    private Integer id;

    private String no;

    private String name;

    private Integer companyId;

    private String product;

    private String brief;

    private String team;

    private String custom;

    private String domain;

    private String competitor;

    private Integer finance;

    private String bp;

    private String remark;

    private String createdBy;

    private String updatedBy;

    private List<TagDTO> tagList;

    private CompanyDTO company;
}
