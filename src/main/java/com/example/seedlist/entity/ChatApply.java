package com.example.seedlist.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_chat_apply")
@Getter
@Setter
public class ChatApply extends BaseEntity{

    private Integer projectId;

    private Integer uid;

}
