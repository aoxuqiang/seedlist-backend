package com.example.seedlist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AdminType {
    /**
     * 超级管理员可以查询所有数据
     */
    SUPPER_ADMIN(1, "超级管理员"),
    /**
     * 普通管理员只能查询自己的数据
     */
    ORDINARY_ADMIN(2, "普通管理员"),
    ;

    private final int type;
    private final String name;
}
