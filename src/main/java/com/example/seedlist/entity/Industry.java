package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_industry")
@Getter
@Setter
public class Industry extends BaseEntity {
    /**
     * 行业名称
     */
    private String name;
}
