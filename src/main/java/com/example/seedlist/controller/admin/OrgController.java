package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Org;
import com.example.seedlist.service.OrgService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/org")
@RestController
public class OrgController extends BaseController<OrgService> {

    protected OrgController(OrgService service) {
        super(service);
    }

    @RequestMapping("/list")
    public Result list() {
        return success(getService().getAll());
    }

    @PostMapping("/save")
    public Result save(@RequestBody Org org) {
        return success(getService().save(org));
    }
}
