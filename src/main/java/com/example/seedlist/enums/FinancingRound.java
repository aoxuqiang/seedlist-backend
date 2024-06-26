package com.example.seedlist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@AllArgsConstructor
@Getter
public enum FinancingRound {
    SEED(1, "种子"),
    ANGLE(2, "天使"),
    PRE_A(3, "Pre-A"),
    A(4, "A"),
    A_PLUS(5,"A+"),
    B(6, "B"),
    C(7,"C"),
    D(8,"D"),
    E(9,"E"),
    F(10,"F"),
    ;


    private final Integer code;

    private final String desc;


    public static String getDesc(Integer code) {
        for (FinancingRound value : FinancingRound.values()) {
            if (Objects.equals(code, value.getCode())) {
                return value.getDesc();
            }
        }
        return null;
    }
}
