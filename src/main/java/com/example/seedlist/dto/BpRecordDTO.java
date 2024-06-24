package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class BpRecordDTO implements Serializable {

    private Integer projectId;

    private Integer uid;

    private String uname;
}
