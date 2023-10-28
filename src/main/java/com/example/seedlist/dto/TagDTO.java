package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class TagDTO implements Serializable {

    private Integer id;

    private String tagName;
}

