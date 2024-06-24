package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.MeetingMapper;
import com.example.seedlist.dto.MeetingDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Meeting;
import com.example.seedlist.entity.Project;
import com.example.seedlist.service.MeetingService;
import com.example.seedlist.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meeting")
public class MeetingController extends BaseController<MeetingService> {

    @Autowired
    private ProjectService projectService;


    protected MeetingController(MeetingService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result meetingList() {
        List<Meeting> meetingList = getService().getAll();
        List<Integer> projectIds = meetingList.stream().map(Meeting::getProjectId).collect(Collectors.toList());
        List<Project> projectList = projectService.findAllById(projectIds);
        Map<Integer,Project> projectMap = projectList.stream().collect(Collectors.toMap(Project::getId, v -> v));
        List<MeetingDTO> result = MeetingMapper.MAPPER.toDTOList(meetingList);
        result.forEach(t -> t.setProjectName(projectMap.get(t.getProjectId()).getName()));
        return success(result);
    }

    @GetMapping("/detail")
    public Result meetingDetail(@RequestParam("id") Integer id) {
        Meeting meeting = getService().getById(id);
        Project project = projectService.getById(meeting.getProjectId());
        MeetingDTO result = MeetingMapper.MAPPER.toDTO(meeting);
        result.setProjectName(project.getName());
        return success(result);
    }

    @GetMapping("/project")
    public Result listProject(@RequestParam("projectId") Integer projectId) {
        List<Meeting> projectMeetings = getService().selectByProject(projectId);
        return success(projectMeetings);
    }

    @PostMapping("/save")
    public Result saveMeeting(@RequestBody Meeting meeting) {
        getService().save(meeting);
        return success();
    }

    @GetMapping("/apply/list")
    public Result applyList(@RequestParam("meetingId") Integer meetingId) {
        return success(getService().queryMeetingApplyList(meetingId));
    }

    @GetMapping("/invite/list")
    public Result inviteList(@RequestParam("meetingId") Integer meetingId) {
        return success(getService().queryMeetingInviteList(meetingId));
    }
}
