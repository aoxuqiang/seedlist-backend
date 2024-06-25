package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_financing")
@Getter
@Setter
public class Financing extends BaseEntity {

    private Integer companyId;

    private Integer turn;

    /**
     * 当前估值
     */
    private Integer valuation;
    /**
     * 本轮融资金额
     */
    private Integer amount;
    /**
     * 融资状态
     */
    private Integer state;
}
