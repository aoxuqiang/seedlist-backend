package com.example.seedlist.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_region")
@Entity
@Getter
@Setter
public class Region extends BaseEntity {

    private String areaCode;

    private String areaName;

    private Integer level;

    private Integer parentId;

    private String cityCode;
}
