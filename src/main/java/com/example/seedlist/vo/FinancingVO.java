package com.example.seedlist.vo;

import com.example.seedlist.entity.Financing;
import com.example.seedlist.enums.FinancingRound;
import lombok.Getter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
public class FinancingVO implements Serializable {

    private String turn;
    /**
     * 当前估值
     */
    private String valuation;
    /**
     * 本轮融资金额
     */
    private String amount;


    public FinancingVO(Financing financing) {
        this.turn = FinancingRound.getDesc(financing.getTurn()) + "轮";
        this.valuation = getMoneyString(financing.getValuation());
        this.amount = getMoneyString(financing.getAmount());
    }

    private String getMoneyString(Integer money) {
        if (money > 100000000) {
            return BigDecimal.valueOf(money).divide(new BigDecimal("100000000")).toPlainString() + "亿";
        } else if (money > 10000) {
            return BigDecimal.valueOf(money).divide(new BigDecimal("10000")).toPlainString() + "万";
        }
        return money + "元";
    }
}
