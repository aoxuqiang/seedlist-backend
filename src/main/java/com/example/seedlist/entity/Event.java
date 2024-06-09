package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_event")
@Getter
@Setter
public class Event extends BaseEntity {

    @Column(name = "user_id")
    private String userid;

    @Column(name = "project_id")
    private Integer projectId;

    /**
     * @see com.example.seedlist.enums.EventType
     */
    private Integer event;

    @Column(name = "event_time")
    private Date eventTime;

    /**
     * 事件处理状态 0：待处理  1-已处理  -1：已拒绝
     */
    private Integer dealStatus;
}
