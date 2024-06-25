package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.req.LoginInfo;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Admin;
import com.example.seedlist.enums.ResultCode;
import com.example.seedlist.service.AdminService;
import com.example.seedlist.util.EncryptionUtil;
import com.example.seedlist.util.JwtUtil;
import com.google.common.collect.Lists;
import io.jsonwebtoken.Claims;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
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
    @PostMapping("/login")
    public Result adminLogin(@RequestBody LoginInfo loginInfo) throws Exception{
        Optional<Admin> adminOptional = getService().getAdminByAccount(loginInfo.getUsername());
        if (!adminOptional.isPresent()) {
            return Result.fail(ResultCode.AUTH_FAIL);
        }
        Admin loginAdmin = adminOptional.get();
        String encryptPasswd = EncryptionUtil.encryptWithAES(loginInfo.getPassword(),loginAdmin.getSecretKey());
        if (!Objects.equals(loginAdmin.getPassword(), encryptPasswd)) {
            return Result.fail(ResultCode.AUTH_FAIL);
        }
        //验证通过颁发token
        Map<String, String> map = new HashMap<>(1);
        map.put("token", jwtUtil.createJwt(String.valueOf(loginAdmin.getId()),
                loginAdmin.getAccount(), new HashMap<>()));
        map.put("nickname", loginAdmin.getNickname());
        return Result.success(map);
    }

    @GetMapping("/info")
    @Cacheable(value = "adminInfo",key = "#token")
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
