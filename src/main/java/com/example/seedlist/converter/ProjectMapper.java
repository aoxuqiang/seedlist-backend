package com.example.seedlist.converter;

import com.example.seedlist.dto.ProjectDTO;
import com.example.seedlist.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {

    ProjectMapper MAPPER = Mappers.getMapper( ProjectMapper.class );

    ProjectDTO toProjectDTO(Project project);


    List<ProjectDTO> toProjectDTOList(List<Project> projectList);

    Project  toProject(ProjectDTO projectDTO);
}
