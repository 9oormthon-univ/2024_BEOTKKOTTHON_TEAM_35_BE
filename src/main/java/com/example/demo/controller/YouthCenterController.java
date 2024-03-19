package com.example.demo.controller;

import com.example.demo.service.YouthCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/youth-center")
public class YouthCenterController {

    @Autowired
    private YouthCenterService youthCenterService;

    @GetMapping("/spaces")
    public String getSpaces(@RequestParam(required = false) Integer display,
                            @RequestParam(required = false) Integer pageIndex,
                            @RequestParam(required = false) String srchSpnm,
                            @RequestParam(required = false) String srchAreaCpvn) {
        return youthCenterService.fetchSpaces(display, pageIndex, srchSpnm, srchAreaCpvn);
    }
}
