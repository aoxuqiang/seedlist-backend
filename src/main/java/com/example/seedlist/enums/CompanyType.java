package com.example.seedlist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CompanyType {
    COMPANY_RZ(1, "融资公司"),
    COMPANY_JZ(2, "竞争对手"),
    COMPANY_TZ(3, "投资机构"),
    ;

    private final Integer type;
    private final String desc;
}
