package com.example.seedlist.controller;

import com.example.seedlist.dto.req.LoginInfo;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Admin;
import com.example.seedlist.enums.ResultCode;
import com.example.seedlist.service.AdminService;
import com.example.seedlist.util.JwtUtil;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AdminController extends BaseController<AdminService> {

    @Resource
    private JwtUtil jwtUtil;

    protected AdminController(AdminService service) {
        super(service);
    }

    /**
     * 登录并返回token
     *
     * @param loginInfo 登录信息
     * @return result 结果
     */
    @PostMapping("/admin/login")
    public Result adminLogin(@RequestBody LoginInfo loginInfo) {
        Optional<Admin> adminOptional = getService().getAdminByAccount(loginInfo.getUsername());
        if (!adminOptional.isPresent()) {
            return Result.fail(ResultCode.AUTH_FAIL);
        }
        Admin loginAdmin = adminOptional.get();
        //TODO验证密码

        //验证通过颁发token
        Map<String, String> map = new HashMap<>(1);
        map.put("token", jwtUtil.createJwt(String.valueOf(loginAdmin.getId()),
                loginAdmin.getAccount(), new HashMap<>()));
        map.put("nickname", loginAdmin.getNickname());
        return Result.success(map);
    }

    @GetMapping("/admin/info")
    public Result adminInfo(@RequestParam("token") String token) {
        Claims claims = jwtUtil.parseJwt(token);
        Admin admin = getService().getById(Integer.parseInt(claims.getId()));
        if (admin != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", admin.getNickname());
            map.put("introduction", "this is a introduction");
            map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            map.put("roles", Lists.newArrayList("admin"));
            return Result.success(map);
        }
        return Result.fail(ResultCode.AUTH_FAIL);
    }
}
