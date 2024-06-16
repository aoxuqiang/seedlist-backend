package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.RegionMapper;
import com.example.seedlist.dto.RegionDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Region;
import com.example.seedlist.service.RegionService;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/region")
public class RegionController extends BaseController<RegionService> {

    public RegionController(RegionService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result companyList() {
        List<RegionDTO> regionDTOS = RegionMapper.MAPPER.toRegionList((getService().getAll()));
        return success(treefy(regionDTOS));
    }

    private List<RegionDTO> treefy(List<RegionDTO> regionDTOList) {
        Map<Integer,List<RegionDTO>> levelMap = regionDTOList.stream().collect(Collectors.groupingBy(RegionDTO::getLevel));
        for (RegionDTO regionDTO : levelMap.get(1)) {
            regionDTO.setChildren( getChildren(regionDTO.getAreaId(),levelMap.get(2)));
        }
        for(RegionDTO regionDTO : levelMap.get(2)) {
            regionDTO.setChildren(getChildren(regionDTO.getAreaId(),levelMap.get(3)));
        }
        List<RegionDTO> result = levelMap.get(1);
        result.sort(Comparator.comparing(RegionDTO::getAreaId));
        return result;
    }

    private List<RegionDTO> getChildren(Integer parentId, List<RegionDTO> regionDTOList) {
        return regionDTOList.stream().filter(t -> Objects.equals(t.getParentId(), parentId)).collect(Collectors.toList());
    }

}
