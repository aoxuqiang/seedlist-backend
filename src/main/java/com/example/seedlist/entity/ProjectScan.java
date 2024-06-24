package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_project_scan")
@Getter
@Setter
public class ProjectScan extends BaseEntity{

    private Integer projectId;

    private Integer uid;
}
