package com.example.seedlist.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_company")
@Entity
@Getter
@Setter
public class Company extends BaseEntity {

    private String name;
    /**
     * 地区Id
     */
    private Integer areaId;
    /**
     * 详细地址
     */
    private String address;
}
