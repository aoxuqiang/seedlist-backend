package com.example.seedlist.converter;

import com.example.seedlist.dto.CompanyDTO;
import com.example.seedlist.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CompanyMapper {
    CompanyMapper MAPPER = Mappers.getMapper(CompanyMapper.class);

    CompanyDTO toCompanyDTO(Company company);

    List<CompanyDTO> toCompanyDTOList(List<Company> companyList);

    Company toCompany(CompanyDTO companyDTO);

}
