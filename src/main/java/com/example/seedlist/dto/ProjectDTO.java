package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectDTO implements Serializable {

    private Integer id;

    private String projectNo;

    private String projectName;

    private CompanyDTO company;

    private String product;

    private String brief;

    private String team;

    private Integer finance;

    private String remark;

    private String createdBy;

    private String updatedBy;

    private List<TagDTO> tags;
}
