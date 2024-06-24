package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectBriefDTO implements Serializable {
    private Integer id;

    private String no;

    private String name;

    private String brief;

    private Integer show;
}
