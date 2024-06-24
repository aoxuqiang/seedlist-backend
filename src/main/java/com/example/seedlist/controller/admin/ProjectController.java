package com.example.seedlist.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.ProjectMapper;
import com.example.seedlist.dto.ProjectDetailDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.*;
import com.example.seedlist.service.*;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
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

    @Autowired
    private BpApplyService bpApplyService;

    @Autowired
    private BpSendService bpSendService;

    @Autowired
    private UserService userService;

    protected ProjectController(ProjectService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result listProject() {
        List<Project> projectList = getService().getAll();
        return success(ProjectMapper.MAPPER.toProjectBriefList(projectList));
    }

    @GetMapping("/detail")
    public Result getProjectDetail(@RequestParam(name = "id") int id) {
        Project project = getService().getById(id);
        ProjectDetailDTO projectDTO = ProjectMapper.MAPPER.toProjectDTO(project);
        if (!StringUtils.isEmpty(project.getTags())) {
            List<Tag> tagList = tagService.selectByIds(
                    Stream.of(project.getTags().split(","))
                            .map(Integer::parseInt).collect(Collectors.toList()));
            projectDTO.setTagList(tagList);
        }
        projectDTO.setCompany(companyService.getById(project.getCompanyId()));
        return success(projectDTO);
    }

    @PostMapping("/save")
    public Result saveProject(@RequestBody ProjectDetailDTO projectDTO) {
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


    @PostMapping("/del")
    public Result saveProject(@RequestParam("id") Integer id) {
        getService().delById(id);
        return success();
    }

    @PostMapping("/updateShow")
    public Result updateShow(@RequestParam("id") Integer id,
                             @RequestParam("show") Integer show) {
        Project project = getService().getById(id);
        project.setShow(show);
        getService().save(project);
        return success();
    }

    @GetMapping("/sendBP")
    @ResponseBody
    public Result sendBP(@RequestParam("project") Integer applyId) {
        BpApply bpApply = bpApplyService.getById(applyId);
        Project project = getService().getById(bpApply.getProjectId());
        User investor = userService.getById(bpApply.getUid());
        String content = String.format("这是项目【%s】的BP\n <a href=\"http://www.dealseedlist.com:8080/api/wx/scanBP?pid=%s\">请查收</a>",
                project.getName(), project.getId());
        wechatService.sendMessage(Lists.newArrayList(investor.getWxUserId()), content);
        //记录发送记录
        BpSend bpSend = new BpSend();
        bpSend.setUid(bpApply.getUid());
        bpSend.setProjectId(bpApply.getProjectId());
        bpSendService.save(bpSend);
        return Result.success();
    }

    @GetMapping("/sendBP2Users")
    @ResponseBody
    public Result sendBP(@RequestParam("projectId") Integer projectId,
                         @RequestParam("uids") String uidStr) {
        Project project = getService().getById(projectId);
        List<Integer> uids = Arrays.stream(uidStr.split(",")).map(Integer::parseInt).collect(Collectors.toList());
        List<User> investorList = userService.queryByIds(uids);
        List<String> wxUserIdList = investorList.stream().map(User::getWxUserId).collect(Collectors.toList());
        String content = String.format("这是项目【%s】的BP\n <a href=\"http://www.dealseedlist.com:8080/api/wx/scanBP?pid=%s\">请查收</a>",
                project.getName(), project.getId());
        wechatService.sendMessage(wxUserIdList, content);
        //记录发送BP记录
        List<BpSend> bpSendList = Lists.newArrayList();
        for (Integer uid : uids) {
            BpSend bpSend = new BpSend();
            bpSend.setProjectId(projectId);
            bpSend.setUid(uid);
            bpSendList.add(bpSend);
        }
        bpSendService.saveAll(bpSendList);
        return Result.success();
    }





}
