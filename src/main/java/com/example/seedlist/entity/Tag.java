package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tag")
@Getter
@Setter
public class Tag extends BaseEntity{

    private String name;
}
