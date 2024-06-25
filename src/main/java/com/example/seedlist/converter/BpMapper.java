package com.example.seedlist.converter;


import com.example.seedlist.dto.BpRecordDTO;
import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.BpSend;
import com.example.seedlist.entity.ProjectScan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BpMapper {
    BpMapper MAPPER = Mappers.getMapper( BpMapper.class );

    BpRecordDTO toDTO(BpApply bpApply);

    List<BpRecordDTO> toApplyList(List<BpApply> bpApplyList);

    BpRecordDTO toDTO(BpSend bpSend);

    List<BpRecordDTO> toSendList(List<BpSend> bpSendList);


    BpRecordDTO toDTO(ProjectScan projectScan);

    List<BpRecordDTO> toScanList(List<ProjectScan> scanList);
}
