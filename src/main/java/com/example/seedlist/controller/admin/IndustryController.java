package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Industry;
import com.example.seedlist.service.IndustryService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/industry")
public class IndustryController extends BaseController<IndustryService> {

    protected IndustryController(IndustryService service) {
        super(service);
    }

    @GetMapping("/list")
    public Result listIndustry() {
        return success(getService().getAll());
    }


    @PostMapping("/save")
    public Result saveProject(@RequestBody Industry industry) {
        getService().save(industry);
        return success();
    }

    @PostMapping("/del")
    public Result delTag(@RequestParam("id") Integer id) {
        getService().delById(id);
        return success();
    }

}
