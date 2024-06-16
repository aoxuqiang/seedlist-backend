package com.example.seedlist.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.CompanyMapper;
import com.example.seedlist.converter.ProjectMapper;
import com.example.seedlist.converter.TagMapper;
import com.example.seedlist.dto.Result;
import com.example.seedlist.dto.ProjectDTO;
import com.example.seedlist.dto.TagDTO;
import com.example.seedlist.entity.Project;
import com.example.seedlist.entity.Tag;
import com.example.seedlist.service.CompanyService;
import com.example.seedlist.service.ProjectService;
import com.example.seedlist.service.TagService;
import com.example.seedlist.service.WechatService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/project")
public class ProjectController extends BaseController<ProjectService> {

    @Autowired
    private WechatService wechatService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CompanyService companyService;

    protected ProjectController(ProjectService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result listProject() {
        List<Project> projectList = getService().getAll();
        List<ProjectDTO> projectDTOS = ProjectMapper.MAPPER.toProjectDTOList(projectList);
        for (int i = 0 ; i < projectList.size(); i++) {
            Project project = projectList.get(i);
            if (!StringUtils.isEmpty(project.getTags())) {
                List<Tag> tagList = tagService.selectByIds(
                        Stream.of(project.getTags().split(","))
                                .map(Integer::parseInt).collect(Collectors.toList()));
                List<TagDTO> tagDTOList = TagMapper.MAPPER.toTagDTOList(tagList);
                projectDTOS.get(i).setTagList(tagDTOList);
            }
        }
        return success(projectDTOS);
    }

    @GetMapping("/detail")
    public Result getProjectDetail(@RequestParam(name = "id") int id) {
        Project project = getService().getById(id);
        ProjectDTO projectDTO = ProjectMapper.MAPPER.toProjectDTO(project);
        if (!StringUtils.isEmpty(project.getTags())) {
            List<Tag> tagList = tagService.selectByIds(
                    Stream.of(project.getTags().split(","))
                            .map(Integer::parseInt).collect(Collectors.toList()));
            List<TagDTO> tagDTOList = TagMapper.MAPPER.toTagDTOList(tagList);
            projectDTO.setTagList(tagDTOList);
        }
        projectDTO.setCompany(CompanyMapper.MAPPER.toCompanyDTO(
                companyService.getById(project.getCompanyId())));
        return success(projectDTO);
    }

    @PostMapping("/save")
    public Result saveProject(@RequestBody ProjectDTO projectDTO) {
        Project project = ProjectMapper.MAPPER.toProject(projectDTO);
        if (CollectionUtil.isNotEmpty(projectDTO.getTagList())) {
            String tags = projectDTO.getTagList().stream()
                    .map(t -> String.valueOf(t.getId()))
                            .collect(Collectors.joining(","));
            project.setTags(tags);
        }

        getService().save(project);
        return success();
    }

    @PostMapping("/sendProject")
    public Result sendProject(@RequestParam("projectId") Integer projectId,
                              @RequestParam("toUser")String toUsers) {
        Project project = getService().getById(projectId);
        String content = String.format("项目上新\n %s-%s \n 核心产品:%s \n 财务数据：%s",
                project.getNo(),project.getName(), project.getProduct(), project.getFinance());
        wechatService.sendMessage(Lists.newArrayList(toUsers.split(",")),content);
        return Result.success();
    }
}
