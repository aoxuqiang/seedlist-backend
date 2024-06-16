package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.Result;
import com.example.seedlist.service.InvestorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/investor")
@RestController
public class InvestorController extends BaseController<InvestorService> {

    protected InvestorController(InvestorService service) {
        super(service);
    }

    @RequestMapping("/list")
    public Result list() {
        return success(getService().getAll());
    }
}
