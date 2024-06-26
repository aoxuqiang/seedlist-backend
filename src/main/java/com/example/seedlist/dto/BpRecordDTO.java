package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BpRecordDTO implements Serializable {

    private Integer projectId;

    private Integer uid;

    private String uname;

    private Date createdTime;
}
