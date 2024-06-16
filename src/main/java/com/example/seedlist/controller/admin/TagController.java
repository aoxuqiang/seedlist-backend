package com.example.seedlist.controller.admin;

import com.example.seedlist.controller.BaseController;
import com.example.seedlist.converter.TagMapper;
import com.example.seedlist.dto.Result;
import com.example.seedlist.dto.TagDTO;
import com.example.seedlist.service.TagService;
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
    public Result listTag() {
        return success(TagMapper.MAPPER.toTagDTOList(getService().getAll()));
    }

    @GetMapping("/detail")
    public Result getProjectDetail(@RequestParam(name = "id") int id) {
        return success(TagMapper.MAPPER.toTagDTO(getService().getById(id)));
    }

    @PostMapping("/save")
    public Result saveProject(@RequestBody TagDTO tagDTO) {
        getService().save(TagMapper.MAPPER.toTag(tagDTO));
        return success();
    }

    @PostMapping("/del")
    public Result delTag(@RequestParam("id") Integer id) {
        getService().delById(id);
        return success();
    }

}
