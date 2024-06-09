package com.example.seedlist.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    APPLY_BP(1,"申请BP")
    ;

    private final int code;
    private final String desc;
}
