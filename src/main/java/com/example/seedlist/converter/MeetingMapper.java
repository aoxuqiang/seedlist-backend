package com.example.seedlist.converter;

import com.example.seedlist.dto.MeetingDTO;
import com.example.seedlist.entity.Meeting;
import com.example.seedlist.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MeetingMapper {

    MeetingMapper MAPPER = Mappers.getMapper( MeetingMapper.class );

    MeetingDTO toDTO(Meeting meeting);

    List<MeetingDTO> toDTOList(List<Meeting> meetingList);
}
