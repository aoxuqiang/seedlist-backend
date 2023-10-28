package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_investor_org")
@Entity
@Getter
@Setter
public class InvestorOrg extends BaseEntity {

    private String orgName;

    private String homeLink;

    private String desc;

    private String createdBy;

    private String updatedBy;
}
