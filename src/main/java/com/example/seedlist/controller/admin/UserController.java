package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.Result;
import com.example.seedlist.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserController extends BaseController<UserService> {

    protected UserController(UserService service) {
        super(service);
    }

    @RequestMapping("/list")
    public Result list() {
        return success(getService().getAll());
    }

    @PostMapping("/org")
    public Result relationOrg(@RequestParam("id") Integer id, @RequestParam("orgId") Integer orgId) {
        getService().updateOrgId(id, orgId);
        return success();
    }
}
