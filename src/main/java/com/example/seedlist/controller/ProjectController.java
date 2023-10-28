package com.example.seedlist.controller;

import com.example.seedlist.converter.ProjectMapper;
import com.example.seedlist.dto.Result;
import com.example.seedlist.dto.ProjectDTO;
import com.example.seedlist.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<ProjectService> {


    protected ProjectController(ProjectService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result listProject() {
        return success(ProjectMapper.MAPPER.toProjectDTOList(getService().getAll()));
    }

    @GetMapping("/detail")
    public Result getProjectDetail(@RequestParam(name = "id") int id) {
        return success(ProjectMapper.MAPPER.toProjectDTO(getService().getById(id)));
    }

    @PostMapping("/save")
    public Result saveProject(@RequestBody ProjectDTO projectDTO) {
        getService().save(ProjectMapper.MAPPER.toProject(projectDTO));
        return success();
    }
}
