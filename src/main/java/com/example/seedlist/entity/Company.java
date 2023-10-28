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

    private String fullName;

    private String shortName;

    private String address;

    private Integer type;
}
