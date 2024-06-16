package com.example.seedlist.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RegionDTO implements Serializable {

    private Integer areaId;

    private String areaName;

    private Integer level;

    private Integer parentId;

    private List<RegionDTO> children;
}
