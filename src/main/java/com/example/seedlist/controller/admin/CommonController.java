package com.example.seedlist.controller.admin;

import cn.hutool.core.collection.CollectionUtil;
import com.example.seedlist.converter.BpMapper;
import com.example.seedlist.dto.BpRecordDTO;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.BpApply;
import com.example.seedlist.entity.BpSend;
import com.example.seedlist.entity.Financing;
import com.example.seedlist.entity.ProjectScan;
import com.example.seedlist.entity.User;
import com.example.seedlist.enums.FinancingRound;
import com.example.seedlist.service.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    @Autowired
    private FinancingService financingService;

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

    @GetMapping("/getMoney")
    public Result getMoney() {
        int sum = financingService.getAll().stream().filter(t -> t.getState() == 0)
                .mapToInt(Financing::getAmount).sum();
        return Result.success(sum);
    }

    /**
     * 查询BP 申请记录
     * @param projectId
     * @return
     */
    @GetMapping("/bpApply")
    @Cacheable(value = "bpApply",key = "#projectId")
    public Result queryApply(@RequestParam("projectId") Integer projectId) {
        List<BpApply> bpApplyList = bpApplyService.queryByProject(projectId);
        List<User> userList = userService.findAllById(bpApplyList.stream().map(BpApply::getUid).collect(Collectors.toList()));
        Map<Integer,String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<BpRecordDTO> result = BpMapper.MAPPER.toApplyList(bpApplyList);
        result.forEach(t-> t.setUname(map.get(t.getUid())));
        CollectionUtil.reverse(result);
        return Result.success(result);
    }

    /**
     * BP 发送记录
     * @param projectId
     * @return
     */
    @GetMapping("/bpSend")
    @Cacheable(value = "bpSend", key = "#projectId")
    public Result querySend(@RequestParam("projectId") Integer projectId) {
        List<BpSend> bpSends = bpSendService.queryByProject(projectId);
        List<User> userList = userService.findAllById(bpSends.stream().map(BpSend::getUid).collect(Collectors.toList()));
        Map<Integer,String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<BpRecordDTO> result = BpMapper.MAPPER.toSendList(bpSends);
        result.forEach(t-> t.setUname(map.get(t.getUid())));
        CollectionUtil.reverse(result);
        return  Result.success(result);
    }

    /**
     * 项目浏览记录
     * @param projectId
     * @return
     */
    @GetMapping("/projectScan")
    @Cacheable(value ="projectScan", key = "#projectId")
    public Result queryScan(@RequestParam("projectId") Integer projectId) {
        List<ProjectScan> projectScans = projectScanService.queryByProject(projectId);
        List<User> userList = userService.findAllById(projectScans.stream().map(ProjectScan::getUid).collect(Collectors.toList()));
        Map<Integer,String> map = userList.stream().collect(Collectors.toMap(User::getId, User::getName));
        List<BpRecordDTO> result = BpMapper.MAPPER.toScanList(projectScans);
        result.forEach(t-> t.setUname(map.get(t.getUid())));
        CollectionUtil.reverse(result);
        return Result.success(result);
    }
}
