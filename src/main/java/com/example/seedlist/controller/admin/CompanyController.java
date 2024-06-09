package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.CompanyMapper;
import com.example.seedlist.dto.CompanyDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.enums.CompanyType;
import com.example.seedlist.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/company")
public class CompanyController extends BaseController<CompanyService> {

    public CompanyController(CompanyService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result companyList() {
        return success(CompanyMapper.MAPPER.toCompanyDTOList(getService().getAll()));
    }

    @GetMapping("/listRZ")
    public Result rzCompanyList() {
        return success(CompanyMapper.MAPPER.toCompanyDTOList(getService().findByType(CompanyType.COMPANY_RZ)));
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
}
