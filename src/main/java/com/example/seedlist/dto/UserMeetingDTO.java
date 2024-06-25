package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserMeetingDTO implements Serializable {
    private Integer id;

    private Integer uid;

    private String uname;

    private Date createdTime;

    private Integer auditStatus;
}
