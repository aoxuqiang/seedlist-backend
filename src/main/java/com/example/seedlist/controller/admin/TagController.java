package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.dto.Result;
import com.example.seedlist.entity.Tag;
import com.example.seedlist.service.TagService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/tag")
public class TagController extends BaseController<TagService> {

    protected TagController(TagService service) {
        super(service);
    }

    @GetMapping("/list")
    @Cacheable("allTag")
    public Result listTag() {
        return success(getService().getAll());
    }

    @GetMapping("/detail")
    public Result getProjectDetail(@RequestParam(name = "id") int id) {
        return success(getService().getById(id));
    }

    @PostMapping("/save")
    @CacheEvict(value = "allTag",allEntries = true)
    public Result saveProject(@RequestBody Tag tag) {
        getService().save(tag);
        return success();
    }

    @PostMapping("/del")
    @CacheEvict(value = "allTag",allEntries = true)
    public Result delTag(@RequestParam("id") Integer id) {
        getService().delById(id);
        return success();
    }

}
