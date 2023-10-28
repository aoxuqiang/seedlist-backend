package com.example.seedlist.converter;

import com.example.seedlist.dto.TagDTO;
import com.example.seedlist.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TagMapper {
    TagMapper MAPPER = Mappers.getMapper( TagMapper.class );

    TagDTO toTagDTO(Tag tag);

    List<TagDTO> toTagDTOList(List<Tag> tagList);

    Tag toTag(TagDTO tagDTO);
}
