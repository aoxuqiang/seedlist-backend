package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class FinancingDTO implements Serializable {
    /**
     * 融资记录id
     */
    private Integer id;
    /**
     * 公司id
     */
    private Integer companyId;
    /**
     * 融资轮次
     */
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
    private Integer status;
}
