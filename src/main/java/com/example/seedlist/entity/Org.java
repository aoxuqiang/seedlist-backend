package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "tb_org")
@Entity
@Getter
@Setter
public class Org extends BaseEntity {

    private String name;

    private String description;

    private String homeLink;
}
