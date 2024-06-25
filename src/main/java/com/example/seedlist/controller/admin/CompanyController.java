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
    public Result companyList() {
        List<CompanyDTO> companyDTOS = CompanyMapper.MAPPER.toCompanyDTOList(getService().getAll());
        for (CompanyDTO companyDTO : companyDTOS) {
            Integer areaId = companyDTO.getAreaId();
            Region l3 = regionService.getById(areaId);
            Region l2 = regionService.getById(l3.getParentId());
            Region l1 = regionService.getById(l2.getParentId());
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
    public Result save(@RequestBody CompanyDTO dto) {
        getService().save(CompanyMapper.MAPPER.toCompany(dto));
        return success();
    }

    @GetMapping("/financing/list")
    public Result getFinancingList(@RequestParam("companyId") Integer companyId) {
        List<Financing> companyFinancing = financingService.getCompanyFinancing(companyId);
        return success(companyFinancing);
    }

    @PostMapping("/financing/save")
    public Result saveFinancing(@RequestBody Financing financing) {
        financingService.save(financing);
        return success();
    }
}
