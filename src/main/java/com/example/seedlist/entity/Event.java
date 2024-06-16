package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_event")
@Getter
@Setter
public class Event extends BaseEntity {

    private String userId;


    private Integer projectId;

    /**
     * @see com.example.seedlist.enums.EventType
     */
    private Integer type;
}
