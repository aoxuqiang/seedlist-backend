package com.example.seedlist.converter;

import com.example.seedlist.dto.ProjectBriefDTO;
import com.example.seedlist.dto.ProjectDetailDTO;
import com.example.seedlist.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ProjectMapper {

    ProjectMapper MAPPER = Mappers.getMapper( ProjectMapper.class );

    ProjectDetailDTO toProjectDTO(Project project);

    ProjectBriefDTO toProjectBrief(Project project);

    List<ProjectBriefDTO> toProjectBriefList(List<Project> projectList);


    List<ProjectDetailDTO> toProjectDTOList(List<Project> projectList);

    Project  toProject(ProjectDetailDTO projectDTO);
}
