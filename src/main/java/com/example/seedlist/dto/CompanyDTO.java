package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CompanyDTO implements Serializable {

    private Integer id;

    private String fullName;

    private String shortName;

    private String address;

    private Integer type;
}
