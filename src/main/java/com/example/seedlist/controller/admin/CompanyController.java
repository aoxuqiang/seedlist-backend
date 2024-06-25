package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.CompanyMapper;
import com.example.seedlist.dto.CompanyDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Financing;
import com.example.seedlist.entity.Region;
import com.example.seedlist.service.CompanyService;
import com.example.seedlist.service.FinancingService;
import com.example.seedlist.service.RegionService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<CompanyService> {

    @Autowired
    private FinancingService financingService;

    public CompanyController(CompanyService service) {
        super(service);
    }

    @Autowired
    private RegionService regionService;

    @GetMapping("/list")
    @Cacheable("companyList")
    public Result companyList() {
        List<CompanyDTO> companyDTOS = CompanyMapper.MAPPER.toCompanyDTOList(getService().getAll());
        List<Region> allRegion = regionService.getAllRegsion();
        for (CompanyDTO companyDTO : companyDTOS) {
            Integer areaId = companyDTO.getAreaId();
            Region l3 = allRegion.stream().filter(t -> t.getId().equals(areaId)).findFirst().orElse(null);
            Region l2 = allRegion.stream().filter(t -> t.getId().equals(l3.getParentId())).findFirst().orElse(null);
            Region l1 = allRegion.stream().filter(t -> t.getId().equals(l2.getParentId())).findFirst().orElse(null);
            companyDTO.setArea(String.join("/",Lists.newArrayList(l1.getAreaName(),l2.getAreaName(),l3.getAreaName())));
            companyDTO.setValues(Lists.newArrayList(l1.getId(),l2.getId(),l3.getId()));
        }
        return success(companyDTOS);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam("id") int id) {
        return success(CompanyMapper.MAPPER.toCompanyDTO(getService().getById(id)));
    }

    @PostMapping("/save")
    @CacheEvict(value = "companyList", allEntries = true)
    public Result save(@RequestBody CompanyDTO dto) {
        getService().save(CompanyMapper.MAPPER.toCompany(dto));
        return success();
    }

    @GetMapping("/financing/list")
    @Cacheable(value = "financingList", key = "#companyId")
    public Result getFinancingList(@RequestParam("companyId") Integer companyId) {
        List<Financing> companyFinancing = financingService.getCompanyFinancing(companyId);
        return success(companyFinancing);
    }

    @PostMapping("/financing/save")
    @CacheEvict(value = "financingList", key = "#financing.getCompanyId()")
    public Result saveFinancing(@RequestBody Financing financing) {
        financingService.save(financing);
        return success();
    }

    @PostMapping("/financing/del")
    @CacheEvict(value = "financingList", key = "#id")
    public Result delFinancing(@RequestParam("id") int id) {
        financingService.delById(id);
        return success();
    }
}
