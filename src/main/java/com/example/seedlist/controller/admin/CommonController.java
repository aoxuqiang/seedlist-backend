package com.example.seedlist.controller.admin;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.seedlist.converter.BpMapper;
import com.example.seedlist.dto.BpRecordDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.BpSend;
import com.example.seedlist.entity.MeetingApply;
import com.example.seedlist.entity.User;
import com.example.seedlist.enums.FinancingRound;
import com.example.seedlist.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/common")
public class CommonController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private BpApplyService bpApplyService;

    @Autowired
    private BpSendService bpSendService;

    @Autowired
    private ProjectScanService projectScanService;

    @Autowired
    private UserService userService;

    @GetMapping("/getRounds")
    public Result getRounds() {
        FinancingRound[] values = FinancingRound.values();
        List<Map<String,Object>> result = Lists.newArrayList();
        for (FinancingRound value : values) {
            Map<String,Object> map = Maps.newHashMap();
            map.put("code", value.getCode());
            map.put("desc", value.getDesc());
            result.add(map);
        }
        return Result.success(result);
    }

    /**
     * 查询BP 申请记录
     * @param projectId
     * @return
     */
    @GetMapping("/bpApply")
    public Result queryApply(@RequestParam("projectId") Integer projectId) {
        List<BpApply> bpApplyList = bpApplyService.queryByProject(projectId);
        List<User> userList = userService.findAllById(bpApplyList.stream().map(BpApply::getUid).collect(Collectors.toList()));
        Map<Integer,String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<BpRecordDTO> result = BpMapper.MAPPER.toDTOList(bpApplyList);
        result.forEach(t-> t.setUname(map.get(t.getUid())));
        return Result.success(result);
    }

    /**
     * BP 发送记录
     * @param projectId
     * @return
     */
    @GetMapping("/bpSend")
    public Result querySend(@RequestParam("projectId") Integer projectId) {
        return  Result.success(bpSendService.queryByProject(projectId));
    }

    /**
     * 项目浏览记录
     * @param projectId
     * @return
     */
    @GetMapping("/projectScan")
    public Result queryScan(@RequestParam("projectId") Integer projectId) {
        return Result.success(projectScanService.queryByProject(projectId));
    }

    /**
     * 会议报名记录
     */
    @GetMapping("/meetingApply")
    public Result queryMeetingApply(@RequestParam("meetingId") Integer meetingId) {

        return null;
    }
}
