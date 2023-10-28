package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_investor")
@Entity
@Getter
@Setter
public class Investor extends BaseEntity {
    /**
     * 投资机构id
     */
    private Integer orgId;

    private String name;

    private String createdBy;

    private String updatedBy;
}
