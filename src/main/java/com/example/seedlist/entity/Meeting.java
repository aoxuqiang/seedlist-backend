package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_meeting")
@Getter
@Setter
public class Meeting extends BaseEntity {

    private Integer projectId;

    private String name;


    private String link;

    private Date startTime;
}
