package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_meeting_apply")
@Getter
@Setter
public class MeetingApply extends BaseEntity{

    private Integer meetingId;

    private Integer uid;

    private Integer auditStatus;
}
