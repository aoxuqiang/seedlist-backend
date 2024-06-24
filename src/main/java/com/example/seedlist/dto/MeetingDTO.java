package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MeetingDTO implements Serializable {

    private Integer id;

    private Integer projectId;

    private String projectName;

    private String name;

    private String link;

    private Date startTime;
}
