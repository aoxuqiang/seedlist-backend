package com.example.seedlist.converter;

import com.example.seedlist.dto.RegionDTO;
import com.example.seedlist.entity.Region;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RegionMapper {
    RegionMapper MAPPER = Mappers.getMapper(RegionMapper.class);

    @Mapping(source = "id", target = "areaId")
    RegionDTO toRegionDTO(Region region);

    List<RegionDTO> toRegionList(List<Region> regionList);

}
