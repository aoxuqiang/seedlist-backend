package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_bp_apply")
@Getter
@Setter
public class BpApply extends BaseEntity{

    private Integer projectId;

    private Integer uid;

}
