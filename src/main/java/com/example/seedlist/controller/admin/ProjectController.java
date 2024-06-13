package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.ProjectMapper;
import com.example.seedlist.dto.Result;
import com.example.seedlist.dto.ProjectDTO;
import com.example.seedlist.entity.Project;
import com.example.seedlist.service.ProjectService;
import com.example.seedlist.service.WechatService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<ProjectService> {

    @Autowired
    private WechatService wechatService;

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

    @PostMapping("/sendProject")
    public Result sendProject(@RequestParam("projectId") Integer projectId,
                              @RequestParam("toUser")String toUsers) {
        Project project = getService().getById(projectId);
        String content = String.format("项目上新\n %s-%s \n 核心产品:%s \n 财务数据：%s",
                project.getProjectNo(),project.getBriefName(), project.getProduct(), project.getFinance());
        wechatService.sendMessage(Lists.newArrayList(toUsers.split(",")),content);
        return Result.success();
    }
}
