package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CompanyDTO implements Serializable {

    private Integer id;

    private String name;

    private Integer areaId;

    private String address;

    private List<Integer> values;

    private String area;
}
